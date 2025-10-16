package com.core.config;

import feign.*;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 用于配置FeignClient
 * 通过 @FeignClient(configuration = FeignConfig.class) 进行引用和配置
 * 详情参考 {@link org.springframework.cloud.openfeign.FeignClientsConfiguration}
 */
@Configuration
public class FeignConfig extends FeignClientsConfiguration {

    /**
     * 配置重试
     */
    @Bean
    public Retryer feignRetryer() {
        // period: 每次重试前延迟时间,多次重试会进行叠加
        // maxPeriod: 重试前最大延迟
        // maxAttempts: 最大重试次数
        return new Retryer.Default(1000,3000,3);
    }


    /**
     * 配置Feign请求的日志等级
     * 详情参考 {@link feign.Logger.Level}
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }


    /**
     * 连接超时配置
     * 详情参考 {@link feign.Request.Options#Options(long, TimeUnit, long, TimeUnit, boolean)}
     */
    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(3, TimeUnit.SECONDS, 10,TimeUnit.SECONDS,true);
    }


    /**
     * 配置请求拦截器
     *
     */
    public RequestInterceptor feignRequestInterceptor() {
        return template -> {

        };
    }
}
