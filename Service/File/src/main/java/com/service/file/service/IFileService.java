package com.service.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.Row;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;
import com.core.doMain.file.UploadsForm;
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
