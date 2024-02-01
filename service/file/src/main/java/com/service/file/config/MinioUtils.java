package com.service.file.config;

import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;

@Component
public class MinioUtils {

    @Autowired
    private MinioClient minioClient;


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
    public void uploading(MultipartFile file, String bucket) throws Exception {
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
