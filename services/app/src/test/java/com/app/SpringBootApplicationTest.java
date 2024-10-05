package com.app;

import com.redis.lock.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
public class SpringBootApplicationTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * 测试Redis分布式锁
     */
    @Test
    public void testRedisLock() throws Exception {
        // 线程1
        Thread thread1 = new Thread(() -> {
            int i = 0;
            while (i < 4) {
                RedisDistributedLock lock = RedisDistributedLock.builder(redisTemplate, "test", 10000);
                // 获取锁
                if (lock.acquireLock()) {
                    i++;
                    log.info("线程1获取锁成功");
                    try {
                        // 模拟执行业务代码...
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }finally {
                        boolean  isReleaseLock = lock.releaseLock();
                        if (isReleaseLock) {
                            log.info("线程1释放锁成功");
                        }else {
                            log.info("线程1释放锁异常");
                        }
                    }
                }
            }
        });

        // 线程1
        Thread thread2 = new Thread(() -> {
            int i = 0;
            while (i < 4) {
                RedisDistributedLock lock = RedisDistributedLock.builder(redisTemplate, "test", 10000);
                // 获取锁
                if (lock.acquireLock()) {
                    i++;
                    log.info("线程2获取锁成功");
                    try {
                        // 模拟执行业务代码...
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }finally {
                        boolean  isReleaseLock = lock.releaseLock();
                        if (isReleaseLock) {
                            log.info("线程2释放锁成功");
                        }else {
                            log.info("线程2释放锁异常");
                        }
                    }
                }
            }
        });

        // 线程1
        Thread thread3 = new Thread(() -> {
            int i = 0;
            while (i < 4) {
                RedisDistributedLock lock = RedisDistributedLock.builder(redisTemplate, "test", 10000);
                // 获取锁
                if (lock.acquireLock()) {
                    i++;
                    log.info("线程3获取锁成功");
                    try {
                        // 模拟执行业务代码...
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }finally {
                        boolean  isReleaseLock = lock.releaseLock();
                        if (isReleaseLock) {
                            log.info("线程3释放锁成功");
                        }else {
                            log.info("线程3释放锁异常");
                        }
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
