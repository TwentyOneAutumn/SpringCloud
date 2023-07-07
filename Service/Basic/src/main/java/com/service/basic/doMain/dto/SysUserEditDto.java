package com.service.basic.doMain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户修改Dto
 */
@Data
public class SysUserEditDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "userId参数不能为空")
    private String userId;


    /**
     * 用户名
     */
    @NotBlank(message = "userName参数不能为空")
    private String userName;


    /**
     * 性别
     */
    private String gender;


    /**
     * 生日
     */
    private Date birthday;
}
