package com.security.authentication.interceptor;

import com.security.enums.FeignRequestHeader;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign远程调用拦截器
 */
public class FeignHandlerInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (!hasHeader(template, FeignRequestHeader.FEIGN_REQUEST_HEADER_KEY)) {
            template.header(FeignRequestHeader.FEIGN_REQUEST_HEADER_KEY, FeignRequestHeader.FEIGN_REQUEST_HEADER_VALUE);
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
