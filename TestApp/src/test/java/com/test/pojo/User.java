package com.test.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Base{

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户个性签名
     */
    private String userSignature;

    /**
     * 登录时间
     */
    private String loginTime;
}
