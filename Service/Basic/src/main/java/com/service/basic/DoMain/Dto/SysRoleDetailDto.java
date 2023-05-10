package com.service.basic.DoMain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色详情Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDetailDto implements Serializable {


    /**
     * 主键ID
     */
    @NotBlank(message = "roleId参数不能为空")
    private String roleId;
}
