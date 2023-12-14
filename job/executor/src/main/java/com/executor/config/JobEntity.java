package com.executor.config;

import org.springframework.transaction.annotation.Transactional;

/**
 * Job接口
 */
public interface JobEntity {

    @Transactional
    void outWork(String jobParam);
}
