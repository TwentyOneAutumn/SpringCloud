package com.service.basic.config;

import com.security.authentication.Interface.AuthenticationAware;
import com.security.authentication.Interface.AuthenticationPostProcessor;
import com.security.authentication.beans.PermissionVerifyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConifg {

    @Bean
    public AuthenticationAware aware(){
        return authentication -> {
            throw new PermissionVerifyException("测试校验失败情况");
        };
    }

    @Bean
    public AuthenticationPostProcessor authenticationPostProcessor(){
        return authentication -> {
            System.out.println("测试AuthenticationPostProcessor");
        };
    }
}
