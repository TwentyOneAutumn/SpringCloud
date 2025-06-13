package com.security.config;

import com.basic.api.RemoteUserService;
import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.dto.UserCodeDto;
import com.core.domain.Result;
import com.core.domain.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 重写Security用户信息类
 */
@Component
public class AuthDetailsService implements UserDetailsService {

    @Autowired
    RemoteUserService remoteUserService;

    /**
     * 根据用户名找用户信息
     */
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        // 验证用户是否存在
        checkUser(userCode);
        // 获取用户信息
        Row<UserDetailInfo> row = remoteUserService.getUserInfo(new UserCodeDto(userCode));
        UserDetailInfo info = row.data(true, new UsernameNotFoundException("获取用户信息异常"));
        // 返回UserDetails对象
        return AuthDetails.build(info);
    }


    /**
     * 判断当前用户是否存在
     */
    private void checkUser(String userCode) throws UsernameNotFoundException{
        Result result = remoteUserService.checkUser(new UserCodeDto(userCode));
        result.isError("当前用户不存在");
    }
}
