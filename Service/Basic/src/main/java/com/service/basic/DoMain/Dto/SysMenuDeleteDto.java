package com.service.basic.DoMain.Dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 菜单删除Dto
 */
@Data
public class SysMenuDeleteDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "menuId参数不能为空")
    private String menuId;
}
