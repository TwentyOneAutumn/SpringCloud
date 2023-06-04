package com.basic.api;

import com.basic.api.doMain.UserInfo;
import com.basic.api.factory.UserFallbackFactory;
import com.core.doMain.Row;
import com.core.doMain.SysUser;
import com.core.enums.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "RemoteUserService",value = ServiceName.BASIC,path = "basic",fallbackFactory = UserFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 根据用户账号获取用户信息
     * @param user 数据对象
     * @return UserInfo
     */
    @PostMapping("/user/info")
    Row<UserInfo> getUserInfo(@RequestBody SysUser user);


    /**
     * 判断当前用户是否存在
     * @param user 数据对象
     * @return Boolean
     */
    @PostMapping("/user/check")
    Row<Boolean> checkUser(@RequestBody SysUser user);
}
