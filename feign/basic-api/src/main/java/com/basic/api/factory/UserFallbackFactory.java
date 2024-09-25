package com.basic.api.factory;

import com.basic.api.RemoteUserService;
import com.basic.api.domain.UserDetailInfo;
import com.basic.api.domain.dto.UserCodeDto;
import com.core.domain.Build;
import com.core.domain.Result;
import com.core.domain.Row;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService(){

            @Override
            public Row<UserDetailInfo> getUserInfo(UserCodeDto userCode) {
                return Build.row(false,"调用基础模块getUserInfo接口异常");
            }

            @Override
            public Result checkUser(UserCodeDto userCode) {
                return Build.result(false,"调用基础模块checkUser接口异常");
            }
        };
    }
}
