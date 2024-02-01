package com.service.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.file.DownLoadForm;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.MultipleFileUploadForm;
import com.core.doMain.file.SingleFileUploadForm;
import com.security.utils.SecurityUtils;
import com.service.file.config.MinioUtils;
import com.service.file.mapper.FileMapper;
import com.service.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件ServiceImpl
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,FileResource> implements IFileService {


    @Autowired
    private MinioUtils minioUtils;


    /**
     * 上传文件
     * @param singleFileUploadForm 文件对象
     * @return Row
     */
    @Override
    public Row<FileResource> singleFileUploading(SingleFileUploadForm singleFileUploadForm) throws Exception {
        MultipartFile file = singleFileUploadForm.getFile();
        String moduleName = singleFileUploadForm.getModuleName();
        FileResource fileResource = uploading(file, moduleName);
        return Build.row(fileResource);
    }


    /**
     * 多文件上传
     *
     * @param multipleFileUploadForm 文件对象
     * @return Row
     */
    @Override
    public Row<List<FileResource>> multipleFileUploading(MultipleFileUploadForm multipleFileUploadForm) throws Exception {
        List<MultipartFile> fileList = multipleFileUploadForm.getFile();
        String moduleName = multipleFileUploadForm.getModuleName();
        List<FileResource> voList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            FileResource fileResource = uploading(file, moduleName);
            voList.add(fileResource);
        }
        return Build.row(voList);
    }


    /**
     * 上传文件通用逻辑抽取
     * @param file 文件对象
     * @param moduleName 模块名称
     * @return FileResource
     */
    public FileResource uploading(MultipartFile file, String moduleName) throws Exception {
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 判断object是否存在
        if(minioUtils.objectExists(moduleName,fileName)){
            // 获取文件信息
            FileResource fileResource = getOne(new LambdaQueryWrapper<FileResource>()
                    .eq(FileResource::getModuleName, moduleName)
                    .eq(FileResource::getFileName, fileName)
            );
            // 更新文件信息
            fileResource.setUploadUserId(SecurityUtils.getUser().getUserId());
            fileResource.setUploadTime(LocalDateTime.now());
            boolean update = updateById(fileResource);
            if(update){
                uploading(file,moduleName);
                return fileResource;
            }else {
                throw new RuntimeException("更新文件信息失败");
            }
        } else {
            FileResource fileResource = new FileResource();
            fileResource.setModuleName(moduleName);
            fileResource.setFileName(fileName);
            // 文件后缀
            if (StrUtil.isNotBlank(fileName) && fileName.contains(".")) {
                String[] split = fileName.split("\\.");
                fileResource.setFilePostfix(split[1]);
            }
            fileResource.setUploadUserId(SecurityUtils.getUser().getUserId());
            fileResource.setUploadTime(LocalDateTime.now());
            boolean save = save(fileResource);
            if (save) {
                minioUtils.uploading(file, moduleName);
                return fileResource;
            } else {
                throw new RuntimeException("新增文件信息失败");
            }
        }
    }


    /**
     * 下载文件
     * @param downLoadForm 数据对象
     * @param response 响应对象
     */
    @Override
    public void downloading(DownLoadForm downLoadForm, HttpServletResponse response) throws Exception {
        FileResource fileResource = getById(downLoadForm.getId());
        if(BeanUtil.isEmpty(fileResource)){
            throw new RuntimeException("文件不存在");
        }
        String moduleName = fileResource.getModuleName();
        String fileName = fileResource.getFileName();
        // 判断object是否存在
        if(minioUtils.objectExists(moduleName,fileName)){
            minioUtils.downloading(moduleName,fileName,response);
        } else {
            throw new RuntimeException("文件不存在");
        }
    }
}
