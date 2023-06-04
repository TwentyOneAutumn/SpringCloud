package com.service.basic.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebTotalInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcTotalConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * 配置 Sentinel拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Add Sentinel interceptor
        addSpringMvcInterceptor(registry);
    }

    private void addSpringMvcInterceptor(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();

        // Depending on your situation, you can choose to process the BlockException via
        // the BlockExceptionHandler or throw it directly, then handle it
        // in Spring web global exception handler.

        // config.setBlockExceptionHandler((request, response, e) -> { throw e; });

        // Use the default handler.
        config.setBlockExceptionHandler(new DefaultBlockExceptionHandler());

        // Custom configuration if necessary
        config.setHttpMethodSpecify(true);
        // By default web context is true, means that unify web context(i.e. use the default context name),
        // in most scenarios that's enough, and it could reduce the memory footprint.
        // If set it to false, entrance contexts will be separated by different URLs,
        // which is useful to support "chain" relation flow strategy.
        // We can change it and view different result in `Resource Chain` menu of dashboard.
        config.setWebContextUnify(true);
        config.setOriginParser(request -> request.getHeader("S-user"));

        // Add sentinel interceptor
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean<Filter> sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }
}
