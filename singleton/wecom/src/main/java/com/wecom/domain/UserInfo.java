package com.wecom.domain;

import lombok.Data;

@Data
public class UserInfo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;


    /**
     * 四字码
     */
    private String unitCode;
}
