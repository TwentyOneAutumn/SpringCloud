package com.service.basic.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SysUserListVo implements Serializable {


    /**
     * 用户名
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
     * 性别
     */
    private Boolean gender;


    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate birthday;
}
