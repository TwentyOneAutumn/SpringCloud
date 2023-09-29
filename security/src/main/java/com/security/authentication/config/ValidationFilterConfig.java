package com.security.authentication.config;

import com.security.authentication.filter.AfterValidationFilter;
import com.security.authentication.beans.AuthenticationExceptionProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import javax.servlet.*;

/**
 * 权限校验过滤器配置类
 */
public class ValidationFilterConfig {

    /**
     * 后置权限校验过滤器
     * 用于处理切面中权限校验过程所发生的异常
     * @return Filter
     */
    @Bean
    @Order
    public Filter afterValidationFilter(AuthenticationExceptionProcessor authenticationExceptionProcessor){
        return new AfterValidationFilter(authenticationExceptionProcessor);
    }
}
