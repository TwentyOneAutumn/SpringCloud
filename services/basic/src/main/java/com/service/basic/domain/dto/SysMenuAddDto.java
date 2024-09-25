package com.service.basic.domain.dto;

import lombok.Data;

/**
 * 菜单新增Dto
 */
@Data
public class SysMenuAddDto {


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
    private String module;
}
