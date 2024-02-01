package com.service.file.controller;

import com.core.doMain.Row;
import com.core.doMain.file.DownLoadForm;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.MultipleFileUploadForm;
import com.core.doMain.file.SingleFileUploadForm;
import com.service.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 文件Controller
 */
@RestController
@RequestMapping
public class FileController {

    @Autowired
    private IFileService fileService;

    /**
     * 单文件上传
     * @param singleFileUploadForm 文件对象
     * @return Row
     */
    @PostMapping("/singleFileUploading")
    public Row<FileResource> singleFileUploading(@Valid @ModelAttribute SingleFileUploadForm singleFileUploadForm) throws Exception {
        return fileService.singleFileUploading(singleFileUploadForm);
    }


    /**
     * 多文件上传
     * @param multipleFileUploadForm 文件对象
     * @return Row
     */
    @PostMapping("/multipleFileUploading")
    public Row<List<FileResource>> multipleFileUploading(@Valid @ModelAttribute MultipleFileUploadForm multipleFileUploadForm) throws Exception {
        return fileService.multipleFileUploading(multipleFileUploadForm);
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
