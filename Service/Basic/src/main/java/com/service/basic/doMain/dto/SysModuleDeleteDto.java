package com.service.basic.doMain.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * 模块删除Dto
 */
@Data
public class SysModuleDeleteDto {

    /**
     * 主键ID
     */
    @NotBlank(message = "moduleId参数不能为空")
    private String moduleId;
}
