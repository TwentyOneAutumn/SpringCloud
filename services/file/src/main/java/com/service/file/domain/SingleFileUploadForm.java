package com.service.file.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件上传实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleFileUploadForm {

    /**
     * 所属模块
     */
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;

    /**
     * 文件
     */
    @NotNull(message = "上传文件不能为空")
    private MultipartFile file;
}