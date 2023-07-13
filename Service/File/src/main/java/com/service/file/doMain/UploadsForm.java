package com.service.file.doMain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 多文件上传实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadsForm {

    /**
     * 上传文件对象集合
     */
    @NotEmpty(message = "上传文件不能为空")
    List<MultipartFile> fileList;

    /**
     * 所属模块
     */
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;
}
