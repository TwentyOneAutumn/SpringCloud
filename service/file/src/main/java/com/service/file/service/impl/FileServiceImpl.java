package com.service.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.file.DownLoadForm;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;
import com.security.utils.SecurityUtils;
import com.service.file.mapper.FileMapper;
import com.service.file.service.IFileService;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;

/**
 * 文件ServiceImpl
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,FileResource> implements IFileService {

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     * @param uploadForm 文件对象
     * @return Row
     */
    @Override
    public Row<FileResource> uploading(UploadForm uploadForm) throws Exception {
        MultipartFile file = uploadForm.getFile();
        String moduleName = uploadForm.getModuleName();
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 判断object是否存在
        if(objectExists(moduleName,fileName)){
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
                return Build.row(fileResource);
            }else {
                throw new RuntimeException("更新文件信息失败");
            }
        } else {
            FileResource fileResource = new FileResource();
            fileResource.setModuleName(moduleName);
            fileResource.setFileName(fileName);
            // 文件后缀
            if(StrUtil.isNotBlank(fileName) && fileName.contains(".")){
                String[] split = fileName.split("\\.");
                fileResource.setFilePostfix(split[1]);
            }
            fileResource.setUploadUserId(SecurityUtils.getUser().getUserId());
            fileResource.setUploadTime(LocalDateTime.now());
            boolean save = save(fileResource);
            if(save){
                uploading(file,moduleName);
                return Build.row(fileResource);
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
        if(objectExists(moduleName,fileName)){
            downloading(moduleName,fileName,response);
        } else {
            throw new RuntimeException("文件不存在");
        }
    }

    /**
     * 判断object是否存在
     * @param bucket 桶名称
     * @param object 文件名称
     * @return 是否存在
     * @throws Exception 异常
     */
    public boolean objectExists(String bucket, String object) {
        try {
            // 获取object元数据
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .build()
            );
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    /**
     * 上传文件到Minio
     * @param file 文件对象
     * @param bucket 桶名称
     * @throws Exception 异常
     */
    public void uploading(MultipartFile file,String bucket) throws Exception {
        // 判断该桶是否存在
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if(!exists){
            // 创建桶
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        // 上传
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(),-1)
                .contentType(file.getContentType())
                .build()
        );
    }

    /**
     * 从Minio下载文件
     * @param bucket 桶名称
     * @param object 文件名称
     * @param response 响应对象
     * @throws Exception 异常
     */
    public void downloading(String bucket, String object, HttpServletResponse response) throws Exception {
        // 判空
        if(!objectExists(bucket,object)){
            throw new NoSuchFileException("目标文件不存在");
        }
        GetObjectResponse inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build()
        );
        // 获取响应的输出流
        OutputStream outputStream = response.getOutputStream();
        // TODO 设置响应头
        // 读取输入流中的数据并写入响应输出流
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        // 刷新输出流
        outputStream.flush();
    }
}
