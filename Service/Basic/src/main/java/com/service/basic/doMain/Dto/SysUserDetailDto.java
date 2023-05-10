package com.service.basic.doMain.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户详情Dto
 */
@Data
public class SysUserDetailDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "userId参数不能为空")
    private String userId;
}
