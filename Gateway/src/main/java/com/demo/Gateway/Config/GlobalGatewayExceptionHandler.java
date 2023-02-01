package com.demo.Gateway.Config;

import com.demo.Core.DoMain.AjaxResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

/**
 * 全局异常处理器
 */
@Slf4j
@Data
public class GlobalGatewayExceptionHandler implements ErrorWebExceptionHandler {

    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    private List<ViewResolver> viewResolvers = Collections.emptyList();
    private ThreadLocal<AjaxResult> threadLocal=new ThreadLocal<>();

    @Override
    public @NotNull Mono<Void> handle(@NotNull ServerWebExchange exchange, @NotNull Throwable ex) {
        String errorMsg;
        if(ex instanceof RuntimeException){
            errorMsg = "服务未找到";
        }
        else if(ex instanceof Exception){
            errorMsg = "服务未找到";
        }
        else {
            errorMsg = "服务未找到";
        }
        threadLocal.set(AjaxResult.error(errorMsg));
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));
    }

    /**
     * 设置返回异常信息
     */
    protected @NotNull Mono<ServerResponse> renderErrorResponse(ServerRequest request){
        return ServerResponse
                // 设置状态码
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                // 设置格式
                .contentType(MediaType.APPLICATION_JSON)
                // 设置返回消息体,从ThreadLocal中获取对象并存入Body
                .body(BodyInserters.fromValue(threadLocal.get()));
    }

    /**
     * 重写write()
     */
    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse()
                // 设置响应格式
                .getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    private class ResponseContext implements ServerResponse.Context {
        private ResponseContext() {
        }

        @Override
        public @NotNull List<HttpMessageWriter<?>> messageWriters() {
            return GlobalGatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public @NotNull List<ViewResolver> viewResolvers() {
            return GlobalGatewayExceptionHandler.this.viewResolvers;
        }
    }
}