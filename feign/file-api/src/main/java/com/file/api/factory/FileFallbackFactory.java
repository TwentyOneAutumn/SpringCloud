package com.file.api.factory;

import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.MultipleFileUploadForm;
import com.core.doMain.file.SingleFileUploadForm;
import com.file.api.RemoteFileService;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

public class FileFallbackFactory implements FallbackFactory<RemoteFileService> {
    @Override
    public RemoteFileService create(Throwable cause) {
        return new RemoteFileService(){

            @Override
            public Row<FileResource> upload(SingleFileUploadForm singleFileUploadForm) {
                return Build.row(false,"调用文件服务upload()方法异常");
            }

            @Override
            public Row<List<FileResource>> uploads(MultipleFileUploadForm multipleFileUploadForm) {
                return Build.row(false,"调用文件服务uploads()方法异常");
            }

            @Override
            public Row<List<FileResource>> toList() {
                return Build.row(false,"调用文件服务toList()方法异常");
            }
        };
    }
}
