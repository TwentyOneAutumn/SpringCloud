package com.service.basic.doMain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色删除Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDeleteDto implements Serializable {


    /**
     * 主键ID
     */
    @NotBlank(message = "roleId参数不能为空")
    private String roleId;
}
