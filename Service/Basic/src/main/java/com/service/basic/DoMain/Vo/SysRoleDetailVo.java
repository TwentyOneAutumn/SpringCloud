package com.service.basic.DoMain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 角色详情Vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDetailVo implements Serializable {


    /**
     * 主键ID
     */
    private String roleId;


    /**
     * 角色名称
     */
    private String roleName;
}
