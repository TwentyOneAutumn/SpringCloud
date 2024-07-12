package com.security.authentication.config;

import com.security.authentication.aspect.AuthenticationAspect;
import com.security.authentication.filter.AfterValidationFilter;
import com.security.authentication.filter.BeforeValidationFilter;
import com.security.authentication.interceptor.FeignHandlerInterceptor;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * 权限校验增强器配置类
 */
public class AuthenticationPostProcessorConfig {

    /**
     * Feign拦截器
     * @return FeignHandlerInterceptor
     */
    @Bean
    public FeignHandlerInterceptor feignHandlerInterceptor(){
        return new FeignHandlerInterceptor();
    }

    /**
     * 后置权限校验过滤器
     * 用于处理切面中权限校验过程所发生的异常
     * @return Filter
     */
    @Bean
    public Filter beforValidationFilter(){
       return new BeforeValidationFilter();
    }

    /**
     * 注册权限校验切面类
     * @return AuthenticationAspect
     */
    @Bean
    public AuthenticationAspect authenticationAspect(){
        return new AuthenticationAspect();
    }


    /**
     * 注册后置权限校验过滤器
     * @return Filter
     */
    @Bean
    public Filter afterValidationFilter(){
        return new AfterValidationFilter();
    }
}
