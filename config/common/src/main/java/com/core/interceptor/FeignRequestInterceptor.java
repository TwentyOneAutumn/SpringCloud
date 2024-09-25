package com.core.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign请求拦截器
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    /**
     * 拦截方法
     * @param requestTemplate 请求对象
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 进行增强处理
    }
}
