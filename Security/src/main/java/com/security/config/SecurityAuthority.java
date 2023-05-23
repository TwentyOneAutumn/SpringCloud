package com.security.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.security.core.GrantedAuthority;

/**
 * 重写Authority权限信息类
 */
public class SecurityAuthority implements GrantedAuthority {


    /**
     * 权限信息
     */
    private String role;


    /**
     * 初始化类权限信息
     * @param role 权限信息
     */
    private SecurityAuthority(String role){
        this.role = role;
    }


    /**
     * 构建SecurityAuthority对象
     * @param role 权限信息
     * @return SecurityAuthority
     */
    public static SecurityAuthority build(String role){
        if(StrUtil.isEmpty(role)){
            throw new RuntimeException("role信息不可为空");
        }
        return new SecurityAuthority(role);
    }


    /**
     * 返回权限信息
     * @return 角色Value值
     */
    @Override
    public String getAuthority() {
        return this.role;
    }
}
