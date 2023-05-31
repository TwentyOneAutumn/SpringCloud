package com.service.basic.doMain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SysUserAddVo {

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate birthday;
}
