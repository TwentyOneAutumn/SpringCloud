package com.security.authentication.Interface;

import com.security.authentication.beans.PermissionVerifyException;
import org.springframework.security.core.Authentication;

/**
 * 权限增强器接口类
 */
public interface AuthenticationPostProcessor {

    /**
     * 该方法用于自定义权限校验
     * @param authentication {@link Authentication} 权限对象
     * @throws PermissionVerifyException 权限校验失败异常
     */
    void postProcess(Authentication authentication) throws PermissionVerifyException;
}
