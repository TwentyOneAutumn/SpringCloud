package com.basic.api.factory;

import com.basic.api.RemoteUserService;
import com.basic.api.doMain.UserInfo;
import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.basic.SysUser;
import org.springframework.cloud.openfeign.FallbackFactory;

public class UserFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService(){

            @Override
            public Row<UserInfo> getUserInfo(SysUser user) {
                return Build.row(false,"调用基础模块getUserInfo接口异常");
            }

            @Override
            public Row<Boolean> checkUser(SysUser user) {
                return Build.row(false,"调用基础模块checkUser接口异常");
            }
        };
    }
}
