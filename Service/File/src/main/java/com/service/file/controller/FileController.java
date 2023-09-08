package com.service.file.controller;

import com.core.doMain.Row;
import com.core.doMain.file.DownLoadForm;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;
import com.service.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 文件Controller
 */
@RestController
@RequestMapping
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 上传文件
     * @param uploadForm 文件对象
     * @return Row
     */
    @PostMapping("/uploading")
    public Row<FileResource> uploading(@Valid @ModelAttribute UploadForm uploadForm) throws Exception {
        return fileService.uploading(uploadForm);
    }

    /**
     * 下载文件
     * @param downLoadForm 数据对象
     * @param response 响应对象
     */
    @PostMapping("/downloading")
    public void downloading(@Valid @RequestBody DownLoadForm downLoadForm, HttpServletResponse response) throws Exception {
        fileService.downloading(downLoadForm,response);
    }
}
