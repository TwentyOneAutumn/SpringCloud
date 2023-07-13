package com.service.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.file.FileResource;
import com.core.doMain.file.UploadForm;
import com.core.doMain.file.UploadsForm;
import com.core.utils.FileUtils;
import com.security.utils.SecurityUtils;
import com.service.file.enums.FilePath;
import com.service.file.mapper.FileMapper;
import com.service.file.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件ServiceImpl
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper,FileResource> implements IFileService {

    /**
     * 上传文件
     *
     * @param uploadForm 文件对象
     * @return Row
     */
    @Override
    public Row<FileResource> upload(UploadForm uploadForm) {
        return Build.row(uploadFile(uploadForm));
    }

    /**
     * 上传多文件
     * @param uploadsForm 文件对象
     * @return Row
     */
    @Override
    public Row<List<FileResource>> uploads(UploadsForm uploadsForm) {
        String moduleName = uploadsForm.getModuleName();
        List<FileResource> fileResourceList = uploadsForm.getFileList().stream().map(multipartFile -> uploadFile(new UploadForm(moduleName, multipartFile))).collect(Collectors.toList());
        return Build.row(fileResourceList);
    }

    /**
     * 上传文件
     * @param uploadForm 文件对象
     * @return FileResource 文件资源对象
     */
    public FileResource uploadFile(UploadForm uploadForm){
        MultipartFile file = uploadForm.getFile();
        String moduleName = uploadForm.getModuleName();
        // 获取文件名称和文件后缀
        String originalFilename = file.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        // 文件名称
        String fileName = split[0];
        // 文件后缀
        String filePostfix = split[1];
        // 获取当前项目路径
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        String projectPath = currentPath.toAbsolutePath().toString();
        // 获取文件夹路径
        String mkdirPath = FileUtils.joinFilePath(projectPath,FilePath.FILE_PATH,moduleName,filePostfix);
        // 创建文件夹
        FileUtils.ofMkdir(mkdirPath);
        // 获取文件路径
        String filePath = FileUtils.joinFilePath(mkdirPath,originalFilename);
        boolean exist = FileUtils.isExist(filePath);
        // 写入文件
        try {
            FileUtils.write(file.getInputStream(),filePath);
        } catch (IOException e) {
            throw new RuntimeException("获取文件输入流异常");
        }
        // 如果文件存在则获取ID
        if(exist){
            // 获取对应FileResource
            FileResource fileResource = getOne(new LambdaQueryWrapper<FileResource>()
                    .eq(FileResource::getFileName, fileName)
                    .eq(FileResource::getFilePostfix, filePostfix)
                    .eq(FileResource::getFilePath, mkdirPath)
                    .eq(FileResource::getModuleName, moduleName)
            );
            if(BeanUtil.isEmpty(fileResource)){
                throw new IllegalStateException("获取文件资源信息异常");
            }
            String id = fileResource.getId();
            // 更新上传人员和上传时间
            boolean update = update(new LambdaUpdateWrapper<FileResource>()
                    .set(FileResource::getUploadUserId, SecurityUtils.getUser().getUserId())
                    .set(FileResource::getUploadTime, LocalDateTime.now())
                    .eq(FileResource::getId, id)
            );
            if(!update){
                throw new IllegalStateException("更新文件资源信息异常");
            }
            // 获取更新后的数据
            FileResource fileResourceUpdated = getById(id);
            if(BeanUtil.isEmpty(fileResourceUpdated)){
                throw new IllegalStateException("获取文件资源信息异常");
            }
            return fileResourceUpdated;
        }else{
            // 处理FileResource
            FileResource fileResource = new FileResource();
            fileResource.setFileName(fileName);
            fileResource.setFilePostfix(filePostfix);
            fileResource.setFilePath(mkdirPath);
            fileResource.setModuleName(moduleName);
            fileResource.setUploadUserId(SecurityUtils.getUser().getUserId());
            fileResource.setUploadTime(LocalDateTime.now());
            boolean save = save(fileResource);
            if(!save){
                throw new IllegalStateException("写入文件资源信息异常");
            }
            return fileResource;
        }
    }
}
