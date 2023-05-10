package com.Core.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;

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
        // 添加服务名请求头
        requestTemplate.header("FeignRequestService",requestTemplate.feignTarget().name());
        // 添加url请求头
        requestTemplate.header("FeignRequestUrl",requestTemplate.url());
    }
}
