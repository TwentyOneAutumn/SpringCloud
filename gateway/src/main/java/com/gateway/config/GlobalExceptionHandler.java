package com.gateway.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.gateway.domain.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局异常处理器
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public @NotNull Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        ResponseError responseError = new ResponseError();
        // 获取请求路径
        String path = exchange.getRequest().getPath().value();
        responseError.setRequestPath(path);
        // 定义异常信息
        String errorMsg = ex.getMessage();
        // 判断是否为服务找不到
        if(ex instanceof NotFoundException && StrUtil.isNotEmpty(errorMsg) && errorMsg.contains("Unable to find instance for")){
            // 设置状态码
            responseError.setCode(503);
            // 截取服务名
            String serviceName = errorMsg.replaceAll("\"", "").replaceAll("503 SERVICE_UNAVAILABLE Unable to find instance for ", "");
            responseError.setMsg("找不到" + serviceName + "服务实例");
        }
        // 日志
        log.error("请求路径:{}",responseError.getRequestPath());
        log.error("错误信息:{}",responseError.getMsg());
        // 异常信息返回
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return response.writeWith(
                Mono.just(response
                        .bufferFactory()
                        .wrap(new JSONObject(responseError)
                                .toString()
                                .getBytes(StandardCharsets.UTF_8)
                        )
                )
        );
    }
}