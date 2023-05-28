package com.core.doMain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色 菜单 关联关系表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
@KeySequence("KeyGenerator")
public class SysRoleMenu {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;


    /**
     * 角色ID
     */
    private String roleId;


    /**
     * 菜单ID
     */
    private String menuId;


    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;

}
