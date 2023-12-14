package com.executor.execute;

import com.executor.config.JobHandler;
import com.executor.service.ITestService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestExecute extends JobHandler {

    @Autowired
    private ITestService testService;

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        execute("测试",testService);
    }
}
