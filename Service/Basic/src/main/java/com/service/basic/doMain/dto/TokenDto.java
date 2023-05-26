package com.service.basic.doMain.dto;

import lombok.Data;

@Data
public class TokenDto {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 用户名
     */
    private String userCode;

    /**
     * 密码
     */
    private String password;
}
