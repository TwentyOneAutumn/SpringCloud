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
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 全局异常处理器
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public @NotNull Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable throwable) {
        ResponseError responseError = new ResponseError();

//        String path = exchange.getRequest().getPath().value();
//        responseError.setRequestPath(path);
//        log.error("请求路径:{}",responseError.getRequestPath());
//        log.error("错误信息:{}",responseError.getMsg());

        // 处理重试异常
        if(throwable instanceof IllegalStateException){
            // 处理重试信息
            String detailMsg = throwable.getMessage();
            String retryMsg = detailMsg.replaceAll("Retries exhausted: ","");
            Throwable exception = throwable.getCause();
            String errorMsg = exception.getMessage();
            // 判断是否为服务找不到
            if(exception instanceof NotFoundException && StrUtil.isNotEmpty(errorMsg) && errorMsg.contains("Unable to find instance for")){
                // 设置状态码
                responseError.setCode(503);
                // 截取服务名
                String serviceName = errorMsg.replaceAll("\"", "").replaceAll("503 SERVICE_UNAVAILABLE Unable to find instance for ", "");
                responseError.setMsg("已重试" + retryMsg + "次,找不到" + serviceName + "服务实例");
            }
        }

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