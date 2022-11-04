package com.demo.Gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class RequestPathGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestPathGatewayFilterFactory.Config> {
    // 无参构造
    public RequestPathGatewayFilterFactory(){super(Config.class);}

    // 过滤逻辑
    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            // 过滤方法
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 前置过滤逻辑

                // 执行其他过滤器
                Mono<Void> result = chain.filter(exchange);

                //后置过滤逻辑

                return result;
            }
        };
    }

    // 当前方法返回简化配置参数命名列表
    @Override
    public List<String> shortcutFieldOrder() {
        // 相当于 RequestPath=name,path
        return Arrays.asList("name","path");
    }

    // 当前过滤器需要使用的配置内容
    public static class Config{
        private String name;
        private String path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
