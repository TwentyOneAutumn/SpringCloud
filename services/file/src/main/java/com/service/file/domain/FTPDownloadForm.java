package com.service.file.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FTPDownloadForm {

    /**
     * 远程文件路径
     */
    @NotBlank(message = "path参数不能为空")
    private String path;


    /**
     * 文件名称
     */
    @NotBlank(message = "fileName参数不能为空")
    private String fileName;
}
