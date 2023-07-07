package com.security.authentication.Interface;

import org.springframework.security.core.Authentication;

/**
 * 权限校验Aware接口
 */
public interface AuthenticationAware {

    /**
     * 校验权限
     * @param authentication {@link Authentication} 权限对象
     */
    void verify(Authentication authentication);
}
