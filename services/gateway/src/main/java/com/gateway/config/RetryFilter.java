package com.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Component
public class RetryFilter implements GlobalFilter, Ordered {

    // 重试间隔时间(单位:秒)
    @Value(value = "${spring.cloud.gateway.filter.retry.interval:#{3}}")
    private Integer interval;

    // 最大重试次数
    @Value("${spring.cloud.gateway.filter.retry.retries:#{3}}")
    private Integer retries;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .doOnError(throwable -> {
                    // 输出日志
                    log.error("异常原因：{}", throwable.getMessage());
                    // 休眠时间
                    try {
                        Thread.sleep(interval * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                // 使用 retryWhen 定义重试的条件和行为
                .retryWhen(Retry
                        // 指定最大的重试次数
                        .max(retries)
                        // 指定触发重试的条件
                        .filter(throwable -> true)
                );
    }

    @Override
    public int getOrder() {
        return -1; // 优先级，越小越先执行
    }

    public void doOnError(){

    }
}