package com.service.file.controller;

import com.basic.api.RemoteUserService;
import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.core.doMain.Row;
import com.core.doMain.SysUser;
import com.service.file.doMain.FileResource;
import com.service.file.service.IFileService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class Test1 {

    @Autowired
    private IFileService fileService;

    @Autowired
    private RemoteUserService remoteUserService;

    @GetMapping("/test1")
    @GlobalTransactional
    public AjaxResult Test1(){
        FileResource fileResource = new FileResource(null,"test","txt","/test","test1","1", LocalDateTime.now());
        boolean save = fileService.save(fileResource);
        Row<Boolean> row = remoteUserService.test(new SysUser());
        boolean error = Row.isError(row);
        if(error){
            throw new RuntimeException("测试");
        }
        throw new RuntimeException("测试");
    }
}
