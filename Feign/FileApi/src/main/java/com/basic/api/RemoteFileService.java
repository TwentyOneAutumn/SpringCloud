package com.basic.api;

import com.basic.api.factory.FileFallbackFactory;
import com.core.doMain.Row;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;
import com.core.doMain.file.UploadsForm;
import com.core.enums.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@FeignClient(contextId = "RemoteFileService",value = ServiceName.FILE,path = "file",fallbackFactory = FileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 上传文件
     * @param uploadForm 文件对象
     * @return Row
     */
    @PostMapping("/upload")
    Row<FileResource> upload(@Valid @ModelAttribute UploadForm uploadForm);

    /**
     * 上传多文件
     * @param uploadsForm 文件对象
     * @return Row
     */
    @PostMapping("/uploads")
    Row<List<FileResource>> uploads(@Valid @ModelAttribute UploadsForm uploadsForm);
}
