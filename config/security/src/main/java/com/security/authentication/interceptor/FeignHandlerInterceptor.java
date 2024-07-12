package com.security.authentication.interceptor;

import cn.hutool.core.bean.BeanUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Feign远程调用拦截器
 */
@Configuration
public class FeignHandlerInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
//        if (!hasHeader(template, FeignRequestHeader.FEIGN_REQUEST_HEADER_KEY)) {
//            template.header(FeignRequestHeader.FEIGN_REQUEST_HEADER_KEY, FeignRequestHeader.FEIGN_REQUEST_HEADER_VALUE);
//        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(BeanUtil.isNotEmpty(attributes)){
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if(BeanUtil.isNotEmpty(headerNames)){
                while (headerNames.hasMoreElements()){
                    String key = headerNames.nextElement();
                    if(!"content-length".equals(key)){
                        String value = request.getHeader(key);
                        template.header(key, value);
                    }
                }
            }
        }
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(),true);
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
