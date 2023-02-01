package com.demo.user.Controller;

import com.demo.Core.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.LoginGetUserDto;
import com.demo.user.DoMain.Dto.LoginUserDto;
import com.demo.user.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    RedisTemplate<String, Object> redisClient;

    /**
     * 用户登录认证
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/auth")
    AjaxResult login(@Valid LoginUserDto dto, HttpServletRequest http) {
        return loginService.login(dto, http);
    }

    /**
     * 获取用户信息
     * @return AjaxResult
     */
    @GetMapping("/getUser")
    AjaxResult getUser(@Valid LoginGetUserDto dto){
        return loginService.getUser(dto);
    }
}
