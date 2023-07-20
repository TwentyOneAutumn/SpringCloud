package com.activemq.publisher.controller;

import com.activemq.config.doMain.MessageInfo;
import com.core.doMain.AjaxResult;
import com.core.doMain.Build;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class TestController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/sendMsg")
    public AjaxResult sendMsg(@Valid @RequestBody MessageInfo msg){
        jmsTemplate.convertAndSend(msg.getTopic(),msg.getMessage());
        return Build.ajax(true);
    }
}
