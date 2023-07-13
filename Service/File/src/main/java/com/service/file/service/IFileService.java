package com.service.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.Row;
import com.service.file.doMain.FileResource;
import com.service.file.doMain.UploadForm;
import com.service.file.doMain.UploadsForm;
import java.util.List;

/**
 * 文件Service
 */
public interface IFileService extends IService<FileResource> {

    /**
     * 上传文件
     *
     * @param uploadForm 文件对象
     * @return Row
     */
    Row<FileResource> upload(UploadForm uploadForm);

    /**
     * 上传多文件
     * @param uploadsForm 文件对象
     * @return Row
     */
    Row<List<FileResource>> uploads(UploadsForm uploadsForm);
}
