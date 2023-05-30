package com.security.config;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import java.util.List;


/**
 * 自定义权限
 */
public class RoleManager extends ProviderManager {

    public RoleManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 执行父类认证方法
        Authentication authenticate = super.authenticate(authentication);
        // 执行自定义权限校验方法
        return authRole(authenticate);
    }

    private Authentication authRole(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        if(BeanUtil.isEmpty(principal)){
            return authentication;
        }
        if(principal instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl)principal;
            // 自定义身份验证逻辑
        }
        return authentication;
    }
}
