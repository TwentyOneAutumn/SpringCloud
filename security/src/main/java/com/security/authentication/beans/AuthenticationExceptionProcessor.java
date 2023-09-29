package com.security.authentication.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限校验异常处理器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationExceptionProcessor {

    /**
     * 是否异常
     */
    private boolean isError;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 异常对象
     */
    private Exception exception;

    /**
     * 初始化
     */
    public void init(){
        this.isError = false;
        this.errorMsg = null;
        this.exception = null;
    }
}
