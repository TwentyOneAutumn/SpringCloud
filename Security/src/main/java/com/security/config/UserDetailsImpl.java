package com.security.config;

import cn.hutool.core.collection.CollUtil;
import com.basic.api.doMain.UserInfo;
import com.core.doMain.SysModule;
import com.core.doMain.SysRole;
import com.core.doMain.SysUser;
import com.core.utils.StreamUtils;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

/**
 * 重写UserDetails
 */
@Data
public class UserDetailsImpl implements UserDetails {

    /**
     * 用户信息
     */
    SysUser user;


    /**
     * 用户角色信息
     */
    Set<SysRole> roleSet;


    /**
     * 用户访问菜单信息
     */
    Set<SysModule> moduleSet;


    private UserDetailsImpl(UserInfo userInfo){
        this.user = userInfo.getUser();
        this.roleSet = userInfo.getRoleSet();
        this.moduleSet = userInfo.getModuleSet();
    }


    /**
     * 构建SecurityUserDetails
     * @param userInfo 数据对象
     * @return SecurityUserDetails
     */
    public static UserDetailsImpl build(UserInfo userInfo){
        return new UserDetailsImpl(userInfo);
    }


    /**
     * 获取用户权限信息
     * @return 用户权限信息
     */
    @Override
    public Set<SecurityAuthority> getAuthorities() {
        Set<SysRole> roleList = this.roleSet;
        if(CollUtil.isNotEmpty(roleList)){
            return StreamUtils.mapToSet(roleList,role -> SecurityAuthority.build(role.getRoleValue()));
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
        return this.user.getPassword();
    }


    /**
     * 获取用户名
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return this.user.getUserName();
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
