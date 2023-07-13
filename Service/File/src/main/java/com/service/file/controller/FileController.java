package com.service.file.controller;

import com.core.doMain.Row;
import com.service.file.doMain.FileResource;
import com.service.file.doMain.UploadForm;
import com.service.file.doMain.UploadsForm;
import com.service.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * 上传文件
     * @param uploadForm 文件对象
     * @return Row
     */
    @PostMapping("/upload")
    public Row<FileResource> upload(@Valid @ModelAttribute UploadForm uploadForm) {
        return fileService.upload(uploadForm);
    }


    /**
     * 上传多文件
     * @param uploadsForm 文件对象
     * @return Row
     */
    @PostMapping("/uploads")
    public Row<List<FileResource>> uploads(@Valid @ModelAttribute UploadsForm uploadsForm) {
        return fileService.uploads(uploadsForm);
    }
}
