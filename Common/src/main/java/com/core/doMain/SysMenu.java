package com.core.doMain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 菜单表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class SysMenu extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单Url
     */
    private String menuUrl;

    /**
     * 所属模块
     */
    private String moduleId;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;
}
