package com.demo.Gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class GlobalFilterImpl implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 前置过滤逻辑

        // 执行其他过滤器
        Mono<Void> result = chain.filter(exchange);

        //后置过滤逻辑

        return result;
    }
}
