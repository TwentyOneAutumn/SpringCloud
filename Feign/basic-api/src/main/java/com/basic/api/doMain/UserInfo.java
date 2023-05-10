package com.basic.api.doMain;

import com.core.doMain.SysMenu;
import com.core.doMain.SysRole;
import com.core.doMain.SysUser;
import lombok.Data;
import java.util.List;

@Data
public class UserInfo{


    /**
     * 用户信息
     */
    SysUser user;


    /**
     * 用户角色信息
     */
    List<SysRole> roleList;


    /**
     * 用户访问菜单信息
     */
    List<SysMenu> menuList;
}
