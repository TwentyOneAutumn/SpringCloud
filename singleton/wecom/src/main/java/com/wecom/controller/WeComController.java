package com.wecom.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.wecom.domain.AuthorizationInfo;
import com.wecom.domain.UserDetail;
import com.wecom.domain.UserInfo;
import com.wecom.domain.dto.LoginDto;
import com.wecom.domain.entry.Build;
import com.wecom.domain.entry.Row;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.rmi.AccessException;

@Slf4j
@RestController
@RequestMapping("/wecom")
public class WeComController {

    @Autowired
    private RequestProxy proxy;


    /**
     * 登录验证
     */
    @GetMapping("/auth")
    public Row<AuthorizationInfo> toAuth(@Valid LoginDto dto) throws AccessException {
        // 授权码
        String code = dto.getCode();
        log.info("授权码:{}", code);
        // 查询企业基本信息
        UserInfo info = proxy.getUserInfo(code);
        // 根据企业微信用户票据查询该用户详情信息
        UserDetail detail = proxy.getUserDetail(info.getUserTicket());
        // 获取手机号
        String mobile = detail.getMobile();
        if(StrUtil.isBlank(mobile)){
            throw new RuntimeException("请在企业微信中设置手机号");
        }
        // 根据手机号获取自定义AccessToken
        AuthorizationInfo token = proxy.authorization(mobile);
        return Build.row(token);
    }

//    /**
//     * 登录验证
//     */
//    @GetMapping("/auth")
//    public Row<AuthorizationInfo> toAuth(@Valid LoginDto dto) throws AccessException {
//        // 授权码
//        String mobile = dto.getCode();
//        // 根据手机号获取自定义AccessToken
//        AuthorizationInfo token = proxy.authorization(mobile);
//        return Build.row(token);
//    }
}
