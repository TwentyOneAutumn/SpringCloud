package com.service.basic.DoMain.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色修改Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleEditDto implements Serializable {


    /**
     * 主键ID
     */
    @NotBlank(message = "参数不能为空")
    private String roleId;


    /**
     * 角色名称
     */
    @NotBlank(message = "参数不能为空")
    private String roleName;
}
