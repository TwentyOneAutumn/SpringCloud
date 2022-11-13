package com.demo.user.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AdminUserAddDto {
    @NotBlank(message = "userName参数不能为空")
    private String userName;
    @NotBlank(message = "passWord参数不能为空")
    private String passWord;
    @NotBlank(message = "role参数不能为空")
    private String role;
}
