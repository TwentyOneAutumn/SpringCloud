package com.demo.user.Config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import com.demo.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
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
                // 循环解密
                for (int i = 0; i < 10; i++) {
                    encodedPassword = Base64.decodeStr(encodedPassword);
                }
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }

    /**
     * 覆盖默认UserDetailsService
     * @return UserDetailsService
     */
    @Override
    protected UserDetailsService userDetailsService() {
        return username -> {
            // 从数据库查询用户信息
            com.demo.user.DoMain.User user = userService.SecurityQueryById(username);
            // 如果为空,代表用户名或密码错误,返回null
            if(BeanUtil.isEmpty(user)){
                return null;
            }
            // 封装用户权限
            ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority(user.getRole()));
            // 将用户信息封装为UserDetails对象并返回
            return new User(user.getUserName(), user.getPassWord(), list);
        };
    }

    /**
     *
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
                .authorizeRequests()
                // 配置登录接口可以匿名访问
                .antMatchers().anonymous()
                // 其他接口都需要经过认证授权才能访问
                .anyRequest().authenticated();
    }
}
