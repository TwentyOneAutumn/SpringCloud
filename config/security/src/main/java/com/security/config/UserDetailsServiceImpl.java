//package com.security.config;
//
//import com.basic.api.RemoteUserService;
//import com.basic.api.domain.UserDetailInfo;
//import com.basic.api.domain.dto.UserCodeDto;
//import com.core.domain.Result;
//import com.core.domain.Row;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * 重写Security用户信息类
// */
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    RemoteUserService remoteUserService;
//
//    /**
//     * 根据用户名找用户信息
//     * @param userCode 用户名
//     * @return UserDetails对象
//     * @throws UsernameNotFoundException 如果找不到用户则抛出异常
//     */
//    @Override
//    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
//        // 验证用户是否存在
//        checkUser(userCode);
//        // 获取用户信息
//        Row<UserDetailInfo> row = remoteUserService.getUserInfo(new UserCodeDto(userCode));
//        UserDetailInfo info = row.data(true, new UsernameNotFoundException("获取用户信息异常"));
//        // 返回UserDetails对象
//        return UserDetailsImpl.build(info);
//    }
//
//
//    /**
//     * 判断当前用户是否存在
//     * @param user 数据对象
//     * @throws UsernameNotFoundException 如果找不到用户则抛出异常
//     */
//    private void checkUser(String userCode) throws UsernameNotFoundException{
//        Result result = remoteUserService.checkUser(new UserCodeDto(userCode));
//        result.isError("当前用户不存在");
//    }
//}
