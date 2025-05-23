package com.wecom.domain;

import lombok.Data;

@Data
public class TokenInfo {

    /**
     * 错误代码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * Token
     */
    private String suite_access_token;

    /**
     * 有效期(秒)
     */
    private Integer expires_in;
}
