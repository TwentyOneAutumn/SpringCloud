package com.server.retry.controller;

import com.server.retry.config.RetryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class RetryController {

    @Autowired
    private RetryBean retryBean;

    @GetMapping("/test")
    public void test() {
        retryBean.test();
    }
}
