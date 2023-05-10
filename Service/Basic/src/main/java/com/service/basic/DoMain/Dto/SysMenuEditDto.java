package com.service.basic.DoMain.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 菜单修改Dto
 */
@Data
public class SysMenuEditDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "menuId参数不能为空")
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
    private String module;
}
