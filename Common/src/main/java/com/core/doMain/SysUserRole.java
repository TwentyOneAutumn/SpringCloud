package com.core.doMain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户 角色 关联关系表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
@KeySequence("KeyGenerator")
public class SysUserRole {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;


    /**
     * 用户ID
     */
    private String userId;


    /**
     * 角色ID
     */
    private String roleId;


    /**
     * 是否删除
     */
    @TableLogic
    private String isDeleted;
}
