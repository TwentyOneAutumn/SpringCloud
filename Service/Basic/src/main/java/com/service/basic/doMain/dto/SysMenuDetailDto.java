package com.service.basic.doMain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 菜单详情Dto
 */
@Data
public class SysMenuDetailDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "menuId参数不能为空")
    private String menuId;
}
