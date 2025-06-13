package com.security.config;

import com.core.domain.Build;
import com.core.utils.ResponseUtils;
import com.security.enums.PermitUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.ClientSecretAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableFeignClients(basePackages = {"com.basic.api"})
public class SecurityConfig {


    /**
     * 用于存储 OAuth 2.0 注册客户端信息的存储库
     */
    @Bean
    public RegisteredClientRepository jdbcRegisteredClientRepository(JdbcTemplate jdbcTemplate){
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }


    /**
     * 负责管理 OAuth 2.0 授权相关事宜
     */
    @Bean
    public RedisOAuth2AuthorizationService authorizationService(RedisConnectionFactory factory) {
        return new RedisOAuth2AuthorizationService(factory);
    }


    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 用于处理身份验证失败的情况
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> {
            exception.printStackTrace();
            log.error("Authentication failure:{}", exception.getMessage());
            ResponseUtils.writer(response, Build.result(false,exception.getMessage()));
        };
    }


    /**
     * 用于授权服务器配置设置的工具
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:8001")
                .build();
    }


    /**
     * OAuth 2.0 Token 生成器
     */
    @Bean
    public OAuth2TokenGenerator<OAuth2Token> uudiOAuth2TokenGenerator(RedisTemplate<String,Object> redisTemplate,RedisOAuth2AuthorizationService authorizationService) {
        return (OAuth2TokenContext context) -> {
            String token = UUID.randomUUID().toString();
            long tokenExpire = 10080L;
            // 获取用户账号
            Authentication authentication = context.getPrincipal();
            AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
            String userCode = authDetails.getUserInfo().getUserCode();
            if(redisTemplate.hasKey(RedisOAuth2AuthorizationService.USER_TOKEN + userCode)){
                // 获取已经生成的Token
                token = authorizationService.deserialize(RedisOAuth2AuthorizationService.USER_TOKEN + userCode,String.class);
                // 获取Key的过期时间
                tokenExpire = redisTemplate.getExpire(RedisOAuth2AuthorizationService.USER_TOKEN + userCode, TimeUnit.MINUTES);
            }
            return new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    token,
                    Instant.now(),
                    Instant.now().plus(tokenExpire, ChronoUnit.MINUTES),
                    context.getAuthorizedScopes()
            );
        };
    }


    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,OAuth2TokenGenerator<OAuth2Token> uudiOAuth2TokenGenerator,OAuth2AuthorizationService authorizationService) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth2/**")
                .authenticated()
                .and()
                // 关闭csrf
                .csrf().disable()
                .formLogin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .apply(new OAuth2AuthorizationServerConfigurer())
                .tokenGenerator(uudiOAuth2TokenGenerator)
                .authorizationService(authorizationService);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthenticationFailureHandler authenticationFailureHandler, AuthenticationProvider userAuthenticationProvider, RedisTokenAuthenticationFilter redisTokenAuthenticationFilter) throws Exception {
        http.
                authorizeRequests()
                // 放行当前配置的URL
                .antMatchers(PermitUrl.UrlArr)
                // 所有人都可以访问
                .permitAll()
                // 其他接口都需要经过认证授权才能访问
                .anyRequest()
                .authenticated()
                .and()
                //
                .addFilterAfter(redisTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf
                .csrf().disable()
                // 不通过Session获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin()
                .failureHandler(authenticationFailureHandler)
                .and()
                .authenticationProvider(userAuthenticationProvider);
        return http.build();
    }


    /**
     * 客户端身份校验器
     */
    @Bean
    public ClientSecretAuthenticationProvider clientSecretAuthenticationProvider(RegisteredClientRepository jdbcRegisteredClientRepository, OAuth2AuthorizationService authorizationService, PasswordEncoder passwordEncoder) {
        ClientSecretAuthenticationProvider provider = new ClientSecretAuthenticationProvider(jdbcRegisteredClientRepository, authorizationService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    /**
     * 用户身份校验器
     */
    @Bean
    public AuthenticationProvider userAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    /**
     * 自定义过滤器
     * 用于校验Token和填充上下文信息
     */
    @Bean
    public RedisTokenAuthenticationFilter redisTokenAuthenticationFilter(RedisOAuth2AuthorizationService authorizationService) {
        return new RedisTokenAuthenticationFilter(authorizationService);
    }
}
