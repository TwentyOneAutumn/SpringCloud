package com.test;

import cn.hutool.core.thread.ExecutorBuilder;
import com.core.doMain.CallableEntity;
import com.core.utils.CallableUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@SpringBootTest
public class TestDemo {

    @Test
    @Transactional
    public void test() throws Exception {
        ExecutorService executor = ExecutorBuilder.create()
                // 设置初始池大小
                .setCorePoolSize(1)
                // 设置最大池大小
                .setMaxPoolSize(5)
                .useSynchronousQueue()
                .build();
        List<Callable<CallableEntity<String>>> callableList = new ArrayList<>();
        callableList.add(() -> {
            try {
                CallableUtil.init("任务1");
                Thread.sleep(3000);
                return CallableUtil.build("任务1执行成功");
            }catch (Exception exception){
                return CallableUtil.error(exception);
            }
        });
        callableList.add(() -> {
            try {
                CallableUtil.init("任务2");
                Thread.sleep(3000);
                return CallableUtil.build("任务1执行成功");
            }catch (Exception exception){
                return CallableUtil.error(exception);
            }
        });
        callableList.add(() -> {
            try {
                CallableUtil.init("任务3");
                Thread.sleep(3000);
                return CallableUtil.build("任务1执行成功");
            }catch (Exception exception){
                return CallableUtil.error(exception);
            }
        });
        List<Future<CallableEntity<String>>> futureList = executor.invokeAll(callableList);
        for (Future<CallableEntity<String>> future : futureList) {
            CallableEntity<String> callableEntity = future.get();
            System.out.println(callableEntity);
        }
    }
}
