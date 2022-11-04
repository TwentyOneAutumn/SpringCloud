package com.demo.Gateway.keyResolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GatewayFilter;

@Component
public class keyResolverImpl implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 获取请求的请求路径地址
        String remoteAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        // 根据客户端地址进行限流
        return Mono.just(remoteAddress);
    }
}
