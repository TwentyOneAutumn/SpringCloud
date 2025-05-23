package com.wecom.domain.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "用户ID不能为空")
    private String userId;
}
