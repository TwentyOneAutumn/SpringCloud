package com.service.file.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class DownLoadForm {

    /**
     * 文件ID
     */
    @NotBlank(message = "id不能为空")
    private String id;
}
