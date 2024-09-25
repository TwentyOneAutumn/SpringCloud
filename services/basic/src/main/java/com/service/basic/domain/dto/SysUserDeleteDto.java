package com.service.basic.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户删除Dto
 */
@Data
public class SysUserDeleteDto {


    /**
     * 主键ID
     */
    @NotBlank(message = "userId参数不能为空")
    private String userId;
}
