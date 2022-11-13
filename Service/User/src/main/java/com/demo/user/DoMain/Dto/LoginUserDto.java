package com.demo.user.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginUserDto {
    @NotBlank(message = "账号不能为空")
    private String userId;
    @NotBlank(message = "密码不能为空")
    private String passWord;
}
