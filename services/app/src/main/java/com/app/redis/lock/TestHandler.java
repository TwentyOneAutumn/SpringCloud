package com.app.redis.lock;

import com.redis.interfaces.RedisMessageHandler;
import com.redis.lock.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

@Slf4j
public class TestHandler implements RedisMessageHandler {

    private final String serviceName;

    private final RedisTemplate<String,Object> redisTemplate;

    public TestHandler(String serviceName,RedisTemplate<String,Object> redisTemplate){
        this.serviceName = serviceName;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void handleMessage(String message, String channel) {
        String lockId = UUID.randomUUID().toString();
        boolean isAcquireLock = RedisLockUtil.acquireLock(redisTemplate, message, lockId, 10000);
        if(isAcquireLock){
            log.info(serviceName + "实例解析了报文:{}",message);
        }
    }
}
