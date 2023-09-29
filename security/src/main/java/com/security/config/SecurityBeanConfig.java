package com.security.config;

import cn.hutool.core.codec.Base64;
import com.core.doMain.Build;
import com.core.utils.ResponseUtils;
import com.core.utils.StrUtils;
import com.core.utils.ThreadUtils;
import com.security.enums.ClientsSql;
import com.security.enums.RedisTokenKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SecurityBean配置类
 */
@Configuration
@EnableFeignClients(basePackages = {"com.basic.api"})
public class SecurityBeanConfig {

    /**
     *
     * @param dataSource 数据源对象
     * @return AuthorizationCodeServices
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * PasswordEncoder
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder(){

            /**
             * 对原始密码进行加密，返回加密后的密码
             * @param rawPassword 密码
             * @return 加密后的密码
             */
            @Override
            public String encode(CharSequence rawPassword) {
                // 循环加密
                for (int i = 0; i < 10; i++) {
                    rawPassword = Base64.encode(rawPassword);
                }
                return rawPassword.toString();
            }


            /**
             * 将登录输入的密码和数据库取出的密码进行比较
             * @param rawPassword 登录输入的密码
             * @param encodedPassword 从数据库获取的加密后的密码
             * @return 密码是否匹配 true:匹配 false:不匹配
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // 将输入密码加密
                String encodeToRawPassword = encode(rawPassword);
                // 将密码进行比较
                return encodedPassword.equals(encodeToRawPassword);
            }
        };
    }

    @Bean
    @ConfigurationProperties(prefix = "client-details.datasource")
    public DataSource clientDetailsServiceDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public TransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 用于管理 OAuth2 客户端的信息
     * 方法名称必须为clientDetailsService，否则Seata找不到clientDetailsService会导致服务启动失败
     * @param clientDetailsServiceDataSource 数据源对象
     * @return clientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource clientDetailsServiceDataSource,PasswordEncoder passwordEncoder){
        JdbcClientDetailsService client = new JdbcClientDetailsService(clientDetailsServiceDataSource);
        // 设置查询客户端详情Sql
        client.setSelectClientDetailsSql(ClientsSql.SELECT_CLIENT_DETAILS_SQL);
        // 设置查询所有客户端Sql
        client.setFindClientDetailsSql(ClientsSql.FIND_CLIENT_DETAILS_SQL);
        // 设置PasswordEncoder
        client.setPasswordEncoder(passwordEncoder);
        return client;
    }

    /**
     * 生成唯一的键（key）用于标识 OAuth2Authentication 对象在Redis中的位置
     * @return AuthenticationKeyGenerator
     */
    @Bean
    public AuthenticationKeyGenerator authenticationKeyGenerator() {
        return new DefaultAuthenticationKeyGenerator() {
            @Override
            public String extractKey(OAuth2Authentication authentication) {
                Map<String, String> map = new LinkedHashMap<>();
                OAuth2Request oAuth2Request = authentication.getOAuth2Request();
                String userName = authentication.getName();
                String ip = ThreadUtils.get("ip",String.class);
                String clientId = oAuth2Request.getClientId();
                map.put(RedisTokenKey.USER_NAME,userName);
                map.put(RedisTokenKey.CLIENT_ID,clientId);
                map.put(RedisTokenKey.IP,ip);
                String key = StrUtils.join(map,":","#");
                return generateKey(key);
            }

            private String generateKey(String key) {
                MessageDigest digest;
                try {
                    digest = MessageDigest.getInstance("MD5");
                    byte[] bytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));
                    return String.format("%032x", new BigInteger(1, bytes));
                } catch (NoSuchAlgorithmException nsae) {
                    throw new IllegalStateException("MD5算法不可用. Fatal (should be in the JDK).", nsae);
                }
            }
        };
    }

    /**
     * 令牌管理
     * @param factory Redis工厂
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory factory,AuthenticationKeyGenerator authenticationKeyGenerator) {
        CustomRedisTokenStore tokenStore = new CustomRedisTokenStore(factory);
        tokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator);
        return tokenStore;
    }

    /**
     * 令牌增强器，用于给令牌添加添加额外的信息或自定义的字段
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                // 自定义增强策略
                return accessToken;
            }
        };
    }


    /**
     * 用于管理和操作授权服务器端的令牌
     * @return AuthorizationServerTokenServices
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(TokenStore tokenStore,ClientDetailsService clientDetailsService){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 设置客户端服务
        tokenServices.setClientDetailsService(clientDetailsService);
        // 支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 设置令牌存储策略
        tokenServices.setTokenStore(tokenStore);
        // 设置访问令牌的过期时间
//        tokenServices.setAccessTokenValiditySeconds(20);
        // 设置刷新令牌的过期时间
//        tokenServices.setRefreshTokenValiditySeconds(20);
        // 令牌增强
//        tokenServices.setTokenEnhancer(tokenEnhancer());
        return tokenServices;
    }

    /**
     * AuthenticationEntryPoint是Spring Security中的一个接口
     * 用于处理未经身份验证的用户试图访问受保护资源时的情况
     * 它定义了一个方法 commence，该方法在未经身份验证的用户访问受保护资源时被调用
     * 主要作用是在用户请求需要身份验证的资源时，如果用户尚未进行身份验证，则自定义身份验证入口点将被触发
     * 身份验证入口点负责决定如何处理未经身份验证的请求
     * @return AuthenticationEntryPoint
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, authException) -> {
            ResponseUtils.writer(response, Build.ajax(false,"认证失败"));
        });
    }

    /**
     * AuthenticationFailureHandler 用于处理身份验证失败的情况
     * 当身份验证失败时，Spring Security将调用AuthenticationFailureHandler的实现来处理失败的结果
     * @return AuthenticationFailureHandler
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> ResponseUtils.writer(response, Build.ajax(false,exception.getMessage()));
    }

}
