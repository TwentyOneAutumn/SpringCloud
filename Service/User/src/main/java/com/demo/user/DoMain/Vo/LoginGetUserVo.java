package com.demo.user.DoMain.Vo;

import lombok.Data;

@Data
public class LoginGetUserVo {
    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String role;
}
