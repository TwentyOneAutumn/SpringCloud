package com.service.basic.doMain.vo;

import lombok.Data;

@Data
public class SysMenuListVo {

    /**
     * 主键ID
     */
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
}
