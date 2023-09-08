package com.core.doMain.file;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DownLoadForm {

    /**
     * 所属模块
     */
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;


    /**
     * 文件名称
     */
    private String fileName;


    /**
     * 版本号
     */
    private String versionId;
}
