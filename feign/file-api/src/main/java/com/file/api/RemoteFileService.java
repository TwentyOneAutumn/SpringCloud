package com.file.api;

import com.core.doMain.AjaxResult;
import com.core.doMain.Row;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.SingleFileUploadForm;
import com.core.doMain.file.MultipleFileUploadForm;
import com.core.enums.ServiceName;
import com.file.api.factory.FileFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@FeignClient(contextId = "RemoteFileService",value = ServiceName.FILE,path = "file",fallbackFactory = FileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 上传文件
     * @param singleFileUploadForm 文件对象
     * @return Row
     */
    @PostMapping("/upload")
    Row<FileResource> upload(@Valid @ModelAttribute SingleFileUploadForm singleFileUploadForm);

    /**
     * 上传多文件
     * @param multipleFileUploadForm 文件对象
     * @return Row
     */
    @PostMapping("/uploads")
    Row<List<FileResource>> uploads(@Valid @ModelAttribute MultipleFileUploadForm multipleFileUploadForm);

    @PostMapping("/testSeata")
    AjaxResult testSeata();
}
