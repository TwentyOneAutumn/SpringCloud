package com.app;

import com.redis.lock.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Slf4j
@SpringBootTest
public class SpringBootApplicationTest {

    @Autowired
    private RedisDistributedLock appLock;

    /**
     * 测试Redis分布式锁
     */
    @Test
    public void testRedisLock() throws Exception {
        // 线程1
        Thread thread1 = new Thread(() -> {
            int i = 0;
            String lockId;
            while (i < 4) {
                lockId = UUID.randomUUID().toString();
                boolean isAcquireLock = appLock.acquireLock(lockId);
                if (isAcquireLock) {
                    i++;
                    log.info("线程1获取锁成功");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    boolean isReleaseLock = appLock.releaseLock(lockId);
                    if (isReleaseLock) {
                        log.info("线程1释放锁成功");
                    }
                }
            }
        });

        // 线程2
        Thread thread2 = new Thread(() -> {
            int i = 0;
            String lockId;
            while (i < 4){
                lockId = UUID.randomUUID().toString();
                boolean isAcquireLock = appLock.acquireLock(lockId);
                if(isAcquireLock){
                    i++;
                    log.info("线程2获取锁成功");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    boolean isReleaseLock = appLock.releaseLock(lockId);
                    if(isReleaseLock){
                        log.info("线程2释放锁成功");
                    }
                }
            }
        });

        // 线程3
        Thread thread3 = new Thread(() -> {
            int i = 0;
            String lockId;
            while (i < 4){
                lockId = UUID.randomUUID().toString();
                boolean isAcquireLock = appLock.acquireLock(lockId);
                if(isAcquireLock){
                    i++;
                    log.info("线程3获取锁成功");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    boolean isReleaseLock = appLock.releaseLock(lockId);
                    if(isReleaseLock){
                        log.info("线程3释放锁成功");
                    }
                }
            }
        });

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();

        // 主线程等待子线程完成
        thread1.join();
        thread2.join();
        thread3.join();
    }
}
