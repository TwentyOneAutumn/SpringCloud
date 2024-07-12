package com.service.basic.doMain.dto;

import lombok.Data;

/**
 * 模块新增Dto
 */
@Data
public class SysModuleAddDto {

    /**
     * 父模块ID
     */
    private String parentId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块Url
     */
    private String moduleUrl;
}
