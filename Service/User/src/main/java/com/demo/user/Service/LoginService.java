package com.demo.user.Service;

import com.demo.Common.DoMain.AjaxResult;
import com.demo.user.DoMain.Dto.LoginUserDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    AjaxResult login(LoginUserDto dto, HttpServletRequest http);
}
