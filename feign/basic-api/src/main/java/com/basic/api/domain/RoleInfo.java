package com.basic.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleInfo implements Serializable {

    /**
     * 主键ID
     */
    private String roleId;


    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 值
     */
    private String roleValue;
}
