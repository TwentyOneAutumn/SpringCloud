package com.basic.api.doMain;

import com.core.doMain.basic.SysModule;
import com.core.doMain.basic.SysRole;
import com.core.doMain.basic.SysUser;
import lombok.Data;
import java.util.Set;

@Data
public class UserInfo{


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
}
