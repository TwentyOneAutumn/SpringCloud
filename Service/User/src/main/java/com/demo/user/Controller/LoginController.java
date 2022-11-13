package com.demo.user.Controller;

import com.demo.Common.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.LoginUserDto;
import com.demo.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 用户登录认证
     *
     * @param dto 数据对象
     * @return AjaxResult
     */
    @GetMapping("/auth")
    AjaxResult login(@Valid LoginUserDto dto, HttpServletRequest http) {
        return userService.login(dto, http);
    }
}
