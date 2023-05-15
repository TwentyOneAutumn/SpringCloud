package com.security.doMain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.basic.api.RemoteUserService;
import com.basic.api.doMain.UserInfo;
import com.core.doMain.Row;
import com.core.doMain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 重写Security用户信息类
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 根据用户名找用户信息
     * @param username 用户名
     * @return UserDetails对象
     * @throws UsernameNotFoundException 如果找不到用户则抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setUserCode(username);
        // 验证用户是否存在
        checkUser(sysUser);
        // 获取用户信息
        Row<UserInfo> userInfoRow = remoteUserService.getUserInfo(sysUser);
        if(Row.isError(userInfoRow)){
            isError("获取用户信息异常");
        }
        UserInfo userInfo = userInfoRow.getRow();
        // 判断用户信息是否合法
        if(BeanUtil.isEmpty(userInfo) || BeanUtil.isEmpty(userInfo.getUser()) || CollUtil.isEmpty(userInfo.getRoleSet())){
            isError("获取用户信息异常");
        }
        // 返回UserDetails对象
        return SecurityUserDetails.build(userInfo);
    }


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @throws UsernameNotFoundException 如果找不到用户则抛出异常
     */
    private void checkUser(SysUser user) throws UsernameNotFoundException{
        Row<Boolean> check = remoteUserService.checkUser(user);
        if(Row.isError(check)){
            isError("当前用户不存在");
        }
    }

    /**
     * 获取用户信息失败,抛出异常
     * @param msg 错误信息
     * @throws UsernameNotFoundException 获取用户信息失败异常
     */
    private void isError(String msg) throws UsernameNotFoundException {
        throw new UsernameNotFoundException(msg);
    }
}