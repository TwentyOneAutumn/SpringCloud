//package com.security.config;
//
//import com.security.enums.PermitUrl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//@Configuration
//public class WebSecurityConfig {
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
//     * 配置 AuthenticationManager
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    /**
//     * 配置身份验证的方式
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // 设置自定义 UserDetailsService 和密码编码器
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    /**
//     * 配置请求
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        // 配置允许匿名访问的 URL
//                        .requestMatchers(PermitUrl.UrlArr).permitAll()
//                        // 其他接口都需要经过认证授权才能访问
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable()) // 关闭 CSRF
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不通过 Session 获取 SecurityContext
//                )
//                .formLogin(form -> form
//                        .failureHandler(authenticationFailureHandler) // 设置登录失败处理器
//                );
//
//        return http.build();
//    }
//
//    /**
//     * 配置忽略的请求（静态资源等）
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers(PermitUrl.UrlArr);
//    }
//}
