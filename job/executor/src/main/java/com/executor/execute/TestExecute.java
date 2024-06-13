package com.executor.execute;

import com.executor.interfaces.Executor;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestExecute implements JobExecutor {


    @Override
    @XxlJob(value = "demoJobHandler")
    @Executor(name = "测试任务", frequency = "每分钟一次")
    public void execute()  throws Exception{
        throw new Exception("测试异常情况");
    }
}
