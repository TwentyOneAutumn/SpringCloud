package com.gateway.config;

import cn.hutool.json.JSONObject;
import com.core.doMain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

/**
 * 全局异常处理器
 */
@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public @NotNull Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        // 获取请求路径
        String path = exchange.getRequest().getPath().value();
        // 日志
        log.error("网关异常:[" + "请求路径:" + path + ",异常信息:" + ex.getMessage() + "]");
        // 异常信息返回
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(new JSONObject(AjaxResult.error(ex.getMessage()))
                                .toString()
                                .getBytes(StandardCharsets.UTF_8)
                        )
                )
        );
    }
}