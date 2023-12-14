package com.security.enums;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign请求头枚举类
 */
public class FeignRequestHeader {

    /**
     * 请求头名称
     */
    public static final String FEIGN_REQUEST_HEADER_KEY = "FeignRequest";

    /**
     * 请求头值
     */
    public static final String FEIGN_REQUEST_HEADER_VALUE = "FeignRequest";

    /**
     * 判断是否为Feign请求
     * @param request 请求对象
     */
    public static boolean isFeignRequest(HttpServletRequest request){
        String header = request.getHeader(FEIGN_REQUEST_HEADER_KEY);
        if(StrUtil.isNotEmpty(header)){
            return FEIGN_REQUEST_HEADER_VALUE.equals(header);
        }
        return false;
    }
}
