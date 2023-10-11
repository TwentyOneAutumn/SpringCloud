package com.core.doMain.basic;

import com.baomidou.mybatisplus.annotation.*;
import com.core.doMain.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class SysUser extends TimeEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String userId;


    /**
     * 用户名
     */
    @TableField("user_name")
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


    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;
}
