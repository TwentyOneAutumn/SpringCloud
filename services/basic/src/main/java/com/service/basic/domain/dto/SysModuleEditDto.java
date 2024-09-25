package com.service.basic.domain.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * 模块修改Dto
 */
@Data
public class SysModuleEditDto {

    /**
     * 主键ID
     */
    @NotBlank(message = "moduleId参数不能为空")
    private String moduleId;

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
