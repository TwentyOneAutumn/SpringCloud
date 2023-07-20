package com.core.doMain.basic;

import com.baomidou.mybatisplus.annotation.*;
import com.core.doMain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class SysRole extends BaseEntity {


    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String roleId;


    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 值
     */
    private String roleValue;


    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;
}