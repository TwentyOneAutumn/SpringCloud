package com.security.authentication.config;

import com.security.authentication.interceptor.FeignHandlerInterceptor;
import com.security.authentication.aspect.AuthenticationAspect;
import com.security.authentication.filter.BeforeValidationFilter;
import com.security.authentication.beans.AuthenticationExceptionProcessor;
import com.security.authentication.Interface.AuthenticationPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import javax.servlet.Filter;
import java.util.List;

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
     * 权限增强异常处理器
     * @return AuthenticationExceptionProcessor
     */
    @Bean
    public AuthenticationExceptionProcessor authenticationExceptionProcessor(){
        return new AuthenticationExceptionProcessor(false,null,null);
    }

    /**
     * 注册权限校验切面类
     * @return AuthenticationAspect
     */
    @Bean
    public AuthenticationAspect authenticationAspect(@Autowired AuthenticationExceptionProcessor authenticationExceptionProcessor, @Autowired(required = false) List<AuthenticationPostProcessor> authenticationPostProcessorList, Filter beforValidationFilter){
        return new AuthenticationAspect(authenticationExceptionProcessor,authenticationPostProcessorList,(BeforeValidationFilter)beforValidationFilter);
    }
}
