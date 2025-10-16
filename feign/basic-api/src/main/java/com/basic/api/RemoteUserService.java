package com.basic.api;

import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.dto.UserCodeDto;
import com.basic.api.factory.UserFallbackFactory;
import com.core.config.FeignConfig;
import com.core.domain.Result;
import com.core.domain.Row;
import com.core.enums.ServiceInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "RemoteUserService",value = ServiceInfo.BASIC,path = ServiceInfo.BASIC_PATH,fallbackFactory = UserFallbackFactory.class,configuration = FeignConfig.class)
public interface RemoteUserService {

    /**
     * 根据用户账号获取用户信息
     */
    @PostMapping("/user/info")
    Row<UserDetailInfo> getUserInfo(@RequestBody UserCodeDto userCode);


    /**
     * 判断当前用户是否存在
     */
    @PostMapping("/user/check")
    Result checkUser(@RequestBody UserCodeDto userCode);
}
