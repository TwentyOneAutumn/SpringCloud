package com.basic.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable {

    /**
     * 主键ID
     */
    private String userId;


    /**
     * 用户名
     */
    private String userName;


    /**
     * 账号
     */
    private String userCode;


    /**
     * 密码
     */
    private String password;


    /**
     * 性别
     */
    private Boolean gender;


    /**
     * 生日
     */
    private Date birthday;
}
