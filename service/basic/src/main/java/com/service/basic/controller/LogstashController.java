package com.service.basic.controller;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.core.doMain.Logstash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/logstash")
public class LogstashController {


    @PostMapping("/send")
    AjaxResult sendMsg(@Valid @RequestBody Logstash logstash){
        logstash.log();
        return Build.ajax(true);
    }
}
