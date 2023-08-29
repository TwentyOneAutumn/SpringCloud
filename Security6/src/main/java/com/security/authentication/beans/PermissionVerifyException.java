package com.security.authentication.beans;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义权限验证失败异常
 */
public class PermissionVerifyException extends AuthenticationException {

    public PermissionVerifyException(String msg) {
        super(msg);
    }
}
