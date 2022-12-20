package com.demo.user.DoMain.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginGetUserDto {
    @NotBlank(message = "userId参数不能为空")
    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String role;
}
