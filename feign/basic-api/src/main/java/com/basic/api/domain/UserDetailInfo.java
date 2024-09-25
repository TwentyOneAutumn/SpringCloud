package com.basic.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDetailInfo implements Serializable {

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 用户角色信息
     */
    Set<RoleInfo> roleInfoSet;


    /**
     * 用户访问菜单信息
     */
    Set<MenuInfo> menuInfoSet;
}
