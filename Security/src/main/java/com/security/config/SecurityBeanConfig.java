package com.security.config;

import cn.hutool.core.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * SecurityBean配置类
 */
@Configuration
public class SecurityBeanConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private TokenEnhancer tokenEnhancer;

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

    /**
     * 用于管理 OAuth2 客户端的信息
     * @param dataSource 数据源对象
     * @return JdbcClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        JdbcClientDetailsService client = new JdbcClientDetailsService(dataSource);
        // 设置查询客户端详情Sql
        client.setSelectClientDetailsSql("");
        // 设置查询所有客户端Sql
        client.setFindClientDetailsSql("");
        // 设置PasswordEncoder
        client.setPasswordEncoder(passwordEncoder);
        return client;
    }

    /**
     * 令牌管理
     * @param factory Redis工厂
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory factory) {
        return new RedisTokenStore(factory);
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
    public AuthorizationServerTokenServices authorizationServerTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 设置客户端服务
        tokenServices.setClientDetailsService(clientDetailsService);
        // 支持刷新令牌
        tokenServices.setSupportRefreshToken(true);
        // 设置令牌存储策略
        tokenServices.setTokenStore(tokenStore);
        // 设置访问令牌的过期时间
        tokenServices.setAccessTokenValiditySeconds(20);
        // 设置刷新令牌的过期时间
        tokenServices.setRefreshTokenValiditySeconds(20);
        // 令牌增强
        tokenServices.setTokenEnhancer(tokenEnhancer);
        return tokenServices;
    }

}
