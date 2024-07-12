package com.service.basic.controller;

import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import com.service.basic.config.PushGatewayClient;
import com.service.basic.doMain.dto.PushGatewayDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/log")
public class TestController {


    @PostMapping("/receive")
    public AjaxResult toReceive(@RequestBody Object object) throws Exception {
        log.info("log内容:{}",object.toString());
        return Build.ajax(true);
    }


    @PostMapping("/send")
    public AjaxResult toSend(@RequestBody PushGatewayDto dto) throws Exception {
        Map<String, String> lableMap = dto.getLableMap();
        long timestamp = System.currentTimeMillis();
        log.info(String.valueOf(timestamp));
        lableMap.put("timestamp", String.valueOf(timestamp));
        PushGatewayClient.push(dto.getName(),dto.getHelpMsg(),timestamp, lableMap);
        return Build.ajax(true);
    }
}
