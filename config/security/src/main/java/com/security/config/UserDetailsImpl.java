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
 * 重写UserDetails
 */
@Data
public class UserDetailsImpl implements UserDetails {

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


    private UserDetailsImpl(UserDetailInfo info){
        this.userInfo = info.getUserInfo();
        this.roleInfoSet = info.getRoleInfoSet();
        this.menuInfoSet = info.getMenuInfoSet();
    }


    /**
     * 构建SecurityUserDetails
     * @param userInfo 数据对象
     * @return SecurityUserDetails
     */
    public static UserDetailsImpl build(UserDetailInfo info){
        return new UserDetailsImpl(info);
    }


    /**
     * 获取用户权限信息
     * @return 用户权限信息
     */
    @Override
    public Set<SecurityAuthority> getAuthorities() {
        if(CollUtil.isNotEmpty(roleInfoSet)){
            return roleInfoSet.stream().map(role -> SecurityAuthority.build(role.getRoleValue())).collect(Collectors.toSet());
        }else {
            return new HashSet<SecurityAuthority>();
        }
    }


    /**
     * 获取用户密码
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }


    /**
     * 获取用户名
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return userInfo.getUserName();
    }


    /**
     * 用户账号是否过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * 用户是否处于锁定状态
     * @return true:未锁定 false:已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    /**
     * 用户凭据是否过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 用户是否启用或禁用
     * @return true:已启用 false:已禁用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
