package com.service.file.controller;

import com.core.domain.Build;
import com.core.domain.Result;
import com.service.file.domain.FTPDownloadForm;
import com.service.file.domain.FTPEntity;
import com.service.file.domain.FTPUploadForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/ftp")
public class FTPController {

    @Autowired
    private FTPEntity ftpEntity;

    /**
     * FTP上传文件
     */
    @PostMapping("/upload")
    public Result upload(@Valid @ModelAttribute FTPUploadForm form) throws IOException {
        FTPClient ftpClient = new FTPClient();

        // 连接到FTP服务器
        ftpClient.connect(ftpEntity.getHost(), ftpEntity.getPort());
        ftpClient.login(ftpEntity.getUsername(), ftpEntity.getPassword());

        // 检查是否成功登录
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            return Build.result(false,"登录FTP服务器失败");
        }

        // 设置为被动模式
        ftpClient.enterLocalActiveMode();
        // 设置文件传输模式为二进制
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        // 创建文件夹
        String path = ftpEntity.getPath();
        ftpClient.makeDirectory(path);

        MultipartFile file = form.getFile();
        String fileName = file.getOriginalFilename();
        // 远程文件路径
        String remoteFilePath = path + File.separator + fileName;
        // 上传文件
        boolean isUpload = ftpClient.storeFile(remoteFilePath, file.getInputStream());
        log.info(ftpClient.getReplyString());
        // 登出
        ftpClient.logout();
        // 关闭FTP连接
        ftpClient.disconnect();

        return Build.result(isUpload);
    }


    /**
     * FTP下载文件
     */
    @PostMapping("/download")
    public void download(@Valid @RequestBody FTPDownloadForm form, HttpServletResponse response) throws IOException {
        FTPClient ftpClient = new FTPClient();

        // 连接到FTP服务器
        ftpClient.connect(ftpEntity.getHost(), ftpEntity.getPort());
        ftpClient.login(ftpEntity.getUsername(), ftpEntity.getPassword());

        // 检查是否成功登录
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            log.error("登录FTP服务器失败");
        }

        // 设置为被动模式
        ftpClient.enterLocalActiveMode();
        // 设置文件传输模式为二进制
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // 设置文件的 MIME 类型
        response.setContentType("application/octet-stream");
        // 设置响应头，告诉浏览器这是一个文件下载
        response.setHeader("Content-Disposition", "attachment; filename=" + form.getFileName());
        // 下载文件
        boolean isSuccess = ftpClient.retrieveFile(form.getPath() + File.separator + form.getFileName(), response.getOutputStream());
        if(!isSuccess){
            log.error("FTP下载文件失败");
        }
        // 登出
        ftpClient.logout();
        // 关闭FTP连接
        ftpClient.disconnect();
    }
}
