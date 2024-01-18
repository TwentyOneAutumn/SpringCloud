package com.service.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.doMain.Row;
import com.core.doMain.file.DownLoadForm;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;

import javax.servlet.http.HttpServletResponse;

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
    Row<FileResource> uploading(UploadForm uploadForm) throws Exception;

    /**
     * 下载文件
     * @param downLoadForm 数据对象
     * @param response 响应对象
     */
    void downloading(DownLoadForm downLoadForm, HttpServletResponse response) throws Exception;
}
