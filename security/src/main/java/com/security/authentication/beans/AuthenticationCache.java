package com.security.authentication.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限校验异常处理器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationCache {

    /**
     * 请求对象
     */
    private HttpServletRequest request;

    /**
     * 响应对象
     */
    private HttpServletResponse response;

    /**
     * 是否异常
     */
    private boolean isError = false;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 异常对象
     */
    private Exception exception;

    /**
     * 构造方法
     * @param request 请求对象
     * @param response 响应对象
     */
    private AuthenticationCache(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * 创建权限缓存对象
     */
    public static AuthenticationCache create(HttpServletRequest request,HttpServletResponse response){
        return new AuthenticationCache(request,response);
    }
}
