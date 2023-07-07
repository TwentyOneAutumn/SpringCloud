package com.service.basic.doMain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
