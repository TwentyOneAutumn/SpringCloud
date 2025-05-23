package com.wecom.controller;

import com.wecom.domain.TokenInfo;
import com.wecom.domain.UserInfo;
import com.wecom.domain.entry.Build;
import com.wecom.domain.entry.Row;
import com.wecom.domain.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 获取Token
     */
    @GetMapping("/test1")
    public TokenInfo test1(){
        TokenInfo pojo = new TokenInfo();
        pojo.setExpires_in(7200);
        pojo.setSuite_access_token("EagXcYJIztF6sfbdmIZCmpaR8ZBsvJEFFNBrWmnD5");
        return pojo;
    }


    /**
     * 获取企业微信用户信息
     */
    @GetMapping("/test2")
    public UserInfoVo test2(){
        UserInfoVo pojo = new UserInfoVo();
        pojo.setUserid("1");
        pojo.setName("张三");
        pojo.setMobile("10086");
        return pojo;
    }


    /**
     * 获取用户信息
     */
    @GetMapping("/test3")
    public Row<UserInfo> test3(){
        UserInfo pojo = new UserInfo();
        pojo.setUserId("1");
        pojo.setUserName("张三");
        pojo.setUnitCode("ZGGG");
        return Build.row(pojo);
    }
}
