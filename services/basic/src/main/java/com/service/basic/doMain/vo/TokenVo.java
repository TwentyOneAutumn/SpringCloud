package com.service.basic.doMain.vo;

import lombok.Data;

@Data
public class TokenVo {

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 用户名
     */
    private String userCode;

    /**
     * 用户名
     */
    private String accessToken;

    /**
     * 用户名
     */
    private String refreshToken;

    /**
     * token类型
     */
    private String tokenType;

    /**
     * IP
     */
    private String ip;
}
