package com.basic.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuInfo implements Serializable {

    /**
     * 主键ID
     */
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单Url
     */
    private String menuUrl;
}
