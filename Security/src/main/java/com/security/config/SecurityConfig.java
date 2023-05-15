package com.security.config;

import cn.hutool.core.codec.Base64;
import com.security.doMain.SecurityAuthenticationProvider;
import com.security.doMain.SecurityPasswordEncoder;
import com.security.doMain.SecurityUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan("com.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityPasswordEncoder passwordEncoder;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private SecurityAuthenticationProvider authenticationProvider;

    @Autowired
    private RoleAuthManager roleAuthManager;

    /**
     * 配置身份验证的方式
     * @param auth 用于构建和配置AuthenticationManager的构建器类
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义UserDetailsService和密码编码器
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        // 设置自定义身份验证逻辑
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 获取配置的AuthenticationManager对象
     * @return 返回一个完全填充了身份验证信息的Authentication对象
     * @throws Exception 异常
     */
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return roleAuthManager;
    }

    /**
     * 重写UserDetailsService
     * @return UserDetailsService
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * 对WebSecurity对象进行一些初始化设置
     * @param web WebSecurity对象
     * @throws Exception 异常
     */
    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
    }

    /**
     * 进行Web安全性相关的配置
     * @param web WebSecurity对象
     * @throws Exception 异常
     */
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
        http
                // 关闭csrf
                .csrf().disable()
                // 不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .and()
                .authorizeRequests()
                // 配置映射
                .antMatchers("/login/auth")
                // 所有人都可以访问
                .permitAll()
                // 其他接口都需要经过认证授权才能访问
                .anyRequest().authenticated();
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
