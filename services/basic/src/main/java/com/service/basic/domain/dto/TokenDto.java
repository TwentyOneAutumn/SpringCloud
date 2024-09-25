package com.service.basic.domain.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class TokenDto {

    /**
     * 客户端ID
     */
    @NotBlank(message = "clientId参数不能为空")
    private String clientId;

    /**
     * 用户名
     */
    @NotBlank(message = "userCode参数不能为空")
    private String userCode;

    /**
     * 密码
     */
    @NotBlank(message = "password参数不能为空")
    private String password;
}
