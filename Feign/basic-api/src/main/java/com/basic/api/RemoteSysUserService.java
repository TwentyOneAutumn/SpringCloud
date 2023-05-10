package com.basic.api;

import com.basic.api.doMain.UserInfo;
import com.core.doMain.Row;
import com.core.doMain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "RemoteSysUserService",value = "School",path = "school")
public interface RemoteSysUserService {

    @PostMapping("/user/info")
    Row<UserInfo> getUserInfo(@RequestBody SysUser user);
}
