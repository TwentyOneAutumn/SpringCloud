package com.security.config;

import cn.hutool.core.codec.Base64;
import com.security.doMain.SecurityUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.ContentNegotiationStrategy;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    protected SecurityConfig() {
        super();
    }

    protected SecurityConfig(boolean disableDefaults) {
        super(disableDefaults);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     * 重写AuthenticationManager
     * @return AuthenticationManager
     * @throws Exception 异常
     */
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new RoleAuthManager();
    }

    /**
     * 重写UserDetailsService
     * @return UserDetailsService
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return new SecurityUserDetailsService();
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 配置请求
     * @param http Http对象
     * @throws Exception 抛出异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // 关闭csrf
//                .csrf().disable()
//                // 不通过Session获取SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // 配置登录接口可以匿名访问
//                .antMatchers("/login/auth").anonymous()
//                // 其他接口都需要经过认证授权才能访问
//                .anyRequest().authenticated();
        http.authorizeRequests(expressionInterceptUrlRegistry -> {

        });
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        super.setApplicationContext(context);
    }

    @Override
    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        super.setTrustResolver(trustResolver);
    }

    @Override
    public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
        super.setContentNegotationStrategy(contentNegotiationStrategy);
    }

    @Override
    public void setObjectPostProcessor(ObjectPostProcessor<Object> objectPostProcessor) {
        super.setObjectPostProcessor(objectPostProcessor);
    }

    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }
}
