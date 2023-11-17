package com.core.doMain.basic;

import com.baomidou.mybatisplus.annotation.*;
import com.core.doMain.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 菜单表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class SysMenu extends TimeEntity {

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