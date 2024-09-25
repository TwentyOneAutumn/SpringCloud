package com.service.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.core.domain.Row;
import com.service.file.domain.DownLoadForm;
import com.service.file.domain.FileResource;
import com.service.file.domain.MultipleFileUploadForm;
import com.service.file.domain.SingleFileUploadForm;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件Service
 */
public interface IFileService extends IService<FileResource> {

    /**
     * 上传文件
     *
     * @param singleFileUploadForm 文件对象
     * @return Row
     */
    Row<FileResource> singleFileUploading(SingleFileUploadForm singleFileUploadForm) throws Exception;

    /**
     * 多文件上传
     *
     * @param multipleFileUploadForm 文件对象
     * @return Row
     */
    Row<List<FileResource>> multipleFileUploading(MultipleFileUploadForm multipleFileUploadForm) throws Exception;

    /**
     * 下载文件
     * @param downLoadForm 数据对象
     * @param response 响应对象
     */
    void downloading(DownLoadForm downLoadForm, HttpServletResponse response) throws Exception;

}
