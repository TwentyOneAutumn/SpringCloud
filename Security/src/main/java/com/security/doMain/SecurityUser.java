package com.security.doMain;

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
public class SecurityUser implements UserDetailsService {

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
            throw new UsernameNotFoundException("获取用户信息异常");
        }
        int code = userInfoRow.getCode();
        return null;
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

    private void isError(String msg) throws UsernameNotFoundException {
        throw new UsernameNotFoundException(msg);
    }

}
