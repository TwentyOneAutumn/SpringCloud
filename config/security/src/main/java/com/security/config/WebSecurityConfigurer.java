//package com.security.config;
//
//import com.security.enums.PermitUrl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;
//
//    /**
//     * 获取配置的AuthenticationManager对象
//     * @return 返回一个完全填充了身份验证信息的Authentication对象
//     * @throws Exception 异常
//     */
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    /**
//     * 配置身份验证的方式
//     * @param auth 用于构建和配置AuthenticationManager的构建器类
//     * @throws Exception 异常
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 设置自定义UserDetailsService和密码编码器
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        // 设置自定义身份验证逻辑
////        auth.authenticationProvider(securityAuthenticationProvider);
//    }
//
//    /**
//     * 配置请求
//     * @param http Http对象
//     * @throws Exception 抛出异常
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 配置映射
//                .antMatchers(PermitUrl.UrlArr)
//                // 所有人都可以访问
//                .permitAll()
//                // 其他接口都需要经过认证授权才能访问
//                .anyRequest()
//                .authenticated()
//                .and()
//                // 关闭csrf
//                .csrf().disable()
//                // 不通过Session获取SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin()
//                .failureHandler(authenticationFailureHandler);
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers(PermitUrl.UrlArr);
//    }
//}
