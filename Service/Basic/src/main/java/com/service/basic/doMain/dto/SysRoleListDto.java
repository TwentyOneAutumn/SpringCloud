package com.service.basic.doMain.dto;

import com.core.doMain.PageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 角色列表Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysRoleListDto extends PageEntity implements Serializable {


    /**
     * 用户ID
     */
    @NotBlank(message = "userId参数不能为空")
    private String userId;
}


