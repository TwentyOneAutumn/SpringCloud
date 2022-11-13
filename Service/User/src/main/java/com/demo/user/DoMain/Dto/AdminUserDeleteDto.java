package com.demo.user.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class AdminUserDeleteDto {
    @NotBlank(message = "userId参数不能为空")
    private String userId;
}
