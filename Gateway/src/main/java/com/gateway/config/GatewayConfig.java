package com.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Gateway网关配置
 */
@Configuration
public class GatewayConfig {

    @Autowired
    RedisTemplate<String, Object> redisClient;

    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler() {
        System.out.println("ErrorWebExceptionHandler加载完毕");
        return new GlobalExceptionHandler();
    }
}