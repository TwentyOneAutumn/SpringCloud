package com.server.retry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryBean {

    @Retryable(maxAttempts = 2,backoff = @Backoff(delay = 1000))
    public String test() {
        // 获取当前重试上下文
        RetryContext context = RetrySynchronizationManager.getContext();
        int retryCount = context != null ? context.getRetryCount() : 0;
        log.info("retryCount={}", retryCount);
        throw new RuntimeException("test");
    }
}
