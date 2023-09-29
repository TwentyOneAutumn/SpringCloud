package com.service.basic.doMain.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * 模块详情Dto
 */
@Data
public class SysModuleDetailDto {

    /**
     * 主键ID
     */
    @NotBlank(message = "moduleId参数不能为空")
    private String moduleId;
}
