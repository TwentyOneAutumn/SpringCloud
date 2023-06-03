package com.service.basic.doMain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserListVo {

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
