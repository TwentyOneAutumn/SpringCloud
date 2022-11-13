package com.demo.user.DoMain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.Common.DoMain.BaseEntitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntitys {

    /**
     * 用户账号
     * 主键ID
     */
    @TableId
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 是否可用
     */
    @TableLogic
    private Boolean isDeleted;
}