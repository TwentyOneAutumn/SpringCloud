package com.demo.user.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Core.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.LoginGetUserDto;
import com.demo.user.DoMain.Dto.LoginUserDto;
import com.demo.user.DoMain.User;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<User> {

    AjaxResult login(LoginUserDto dto, HttpServletRequest http);

    AjaxResult getUser(LoginGetUserDto dto);
}
