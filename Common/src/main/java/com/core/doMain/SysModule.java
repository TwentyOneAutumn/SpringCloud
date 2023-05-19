package com.core.doMain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 模块表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_module")
@EqualsAndHashCode(callSuper = true)
@KeySequence("KeyGenerator")
public class SysModule extends BaseEntity{

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String moduleId;

    /**
     * 父模块ID
     */
    private String parentId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块Url
     */
    private String moduleUrl;

    /**
     * 是否删除
     */
    @TableLogic
    private String isDeleted;
}
