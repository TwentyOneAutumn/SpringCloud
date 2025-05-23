package com.server.retry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryBean {

    @Retryable(maxAttempts = 5,backoff = @Backoff(delay = 1000))
    public String test() {
        if(System.currentTimeMillis() % 2 == 0){
            throw new RuntimeException("test");
        }
        return "Yes";
    }
}
