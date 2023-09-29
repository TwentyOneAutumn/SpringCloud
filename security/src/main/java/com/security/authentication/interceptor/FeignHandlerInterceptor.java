package com.security.authentication.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign远程调用拦截器
 */
public class FeignHandlerInterceptor implements RequestInterceptor {

    private final String FEIGN_REQUEST_HEADER = "FeignRequest";

    @Override
    public void apply(RequestTemplate template) {
        if (!hasHeader(template, FEIGN_REQUEST_HEADER)) {
            template.header(FEIGN_REQUEST_HEADER, FEIGN_REQUEST_HEADER);
        }
    }

    /**
     * 判断是否存在Header头
     * @param template 请求模版对象
     * @param headerName Header头名称
     * @return boolean
     */
    private boolean hasHeader(RequestTemplate template, String headerName) {
        return template.headers().containsKey(headerName);
    }
}
