package com.file.api.factory;

import com.core.domain.Build;
import com.core.domain.Result;
import com.core.domain.Row;
import com.file.api.RemoteFileService;
import com.file.api.domain.FileInfo;
import com.file.api.domain.MultipleFileInfo;
import com.file.api.domain.SingleFileInfo;
import com.file.api.domain.Test;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

public class FileFallbackFactory implements FallbackFactory<RemoteFileService> {
    @Override
    public RemoteFileService create(Throwable cause) {
        return new RemoteFileService(){

            @Override
            public Row<FileInfo> upload(SingleFileInfo info) {
                return Build.row(false,"调用文件服务upload()方法异常");
            }

            @Override
            public Row<List<FileInfo>> uploads(MultipleFileInfo info) {
                return Build.row(false,"调用文件服务uploads()方法异常");
            }

            @Override
            public Result toTest(Test test) {
                return Build.result(false,"toTest()方法异常");
            }
        };
    }
}
