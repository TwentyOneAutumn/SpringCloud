package com.service.basic.DoMain.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 用户新增Dto
 */
@Data
public class SysUserAddDto {


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
