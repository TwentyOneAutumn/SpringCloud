package com.service.basic.controller;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.core.interfaces.Module;
import com.service.basic.doMain.dto.SysUserAddDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Module("日志")
@RestController
@RequestMapping("/logstash")
public class LogstashController {


    /**
     * 测试未定义异常情况
     */
    @PostMapping("/toException")
    public AjaxResult toException() throws Exception {
        throw new Exception("测试未定义异常情况");
    }


    /**
     * 测试参数校验异常情况
     */
    @PostMapping("/toBindException")
    public AjaxResult toBindException(@Valid @RequestBody SysUserAddDto dto) throws Exception {
        return Build.ajax(true);
    }


    /**
     * 测试运行时异常情况
     */
    @PostMapping("/toRuntimeException")
    public AjaxResult toRuntimeException() throws Exception {
        throw new RuntimeException("测试运行时异常情况");
    }


    /**
     * 测试空指针异常情况
     */
    @PostMapping("/toNullPointerException")
    public AjaxResult toNullPointerException() throws Exception {
        throw new NullPointerException("测试空指针异常情况");
    }
}
