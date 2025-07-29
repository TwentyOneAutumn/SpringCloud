package com.security.config;

import cn.hutool.core.collection.CollUtil;
import com.basic.api.domain.MenuInfo;
import com.basic.api.domain.RoleInfo;
import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.UserInfo;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 身份信息详情
 */
@Data
public class AuthDetails implements UserDetails {

    /**
     * 用户信息
     */
    UserInfo userInfo;


    /**
     * 用户角色信息
     */
    Set<RoleInfo> roleInfoSet;


    /**
     * 用户访问菜单信息
     */
    Set<MenuInfo> menuInfoSet;


    private AuthDetails(UserDetailInfo info){
        this.userInfo = info.getUserInfo();
        this.roleInfoSet = info.getRoleInfoSet();
        this.menuInfoSet = info.getMenuInfoSet();
    }


    /**
     * 构建SecurityUserDetails
     */
    public static AuthDetails build(UserDetailInfo info){
        return new AuthDetails(info);
    }


    /**
     * 获取用户权限信息
     */
    @Override
    public Set<SecurityAuthority> getAuthorities() {
        if(CollUtil.isNotEmpty(roleInfoSet)){
            return roleInfoSet.stream().map(role -> SecurityAuthority.build(role.getRoleValue())).collect(Collectors.toSet());
        }else {
            return new HashSet<>();
        }
    }


    /**
     * 获取用户密码
     */
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }


    /**
     * 获取用户名
     */
    @Override
    public String getUsername() {
        return userInfo.getUserCode();
    }


    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * 用户是否处于锁定状态
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 用户凭据是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 用户是否启用或禁用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
