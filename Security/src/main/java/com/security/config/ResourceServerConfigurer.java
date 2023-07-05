package com.security.config;

import com.security.enums.PermitUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Spring Security提供的一个配置类，用于配置资源服务器的行为和安全性
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 该方法用于配置资源服务器的安全性，可以设置资源服务器的资源ID和访问令牌验证服务
     * @param resources configurer for the resource server
     * @throws Exception 异常
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("")
                .tokenStore(tokenStore)
                .stateless(true);
        // 设置未身份验证响应处理
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     * 该方法用于配置资源服务器的安全策略。您可以定义哪些请求需要进行认证和授权，以及如何验证访问令牌
     * @param http the current http filter configuration
     * @throws Exception 异常
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PermitUrl.UrlArr)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
