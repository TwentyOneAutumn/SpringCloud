package com.security.utils;

import cn.hutool.core.bean.BeanUtil;
import com.basic.api.domain.UserInfo;
import com.security.config.AuthDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security上下文工具类
 */
public class SecurityUtils {

    /**
     * 获取上下文中的Authentication对象
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 从上下文中获取当前用户对象
     */
    public static UserInfo getUser(){
        Object principal = getAuthentication().getPrincipal();
        if(BeanUtil.isNotEmpty(principal) && principal instanceof AuthDetails){
            AuthDetails userDetails = (AuthDetails) principal;
            return userDetails.getUserInfo();
        }else {
            throw new IllegalStateException("获取用户信息异常");
        }
    }
}
