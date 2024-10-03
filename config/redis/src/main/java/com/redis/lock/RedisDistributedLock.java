package com.redis.lock;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.StandardCharsets;

public class RedisDistributedLock {

    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 锁的名称
     */
    private final String lockKey;

    /**
     * 锁过期时间
     * 单位:秒
     */
    private final long lockExpireTime;

    /**
     *
     * @param redisTemplate redisTemplate
     * @param lockKey 锁的名称
     * @param lockExpireTime 锁过期时间 单位:秒
     *
     */
    public RedisDistributedLock(RedisTemplate<String,Object> redisTemplate, String lockKey, long lockExpireTime) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.lockExpireTime = lockExpireTime * 1000;
    }

    /**
     * 获取指定的锁
     * @param lockId 持有锁的唯一表示
     * @return 是否获取到锁
     */
    public boolean acquireLock(String lockId) {
        if(StrUtil.isEmpty(lockId)){
            throw new RuntimeException("锁ID不能为空");
        }
        // 通过RedisCallback执行SET NX PX命令，确保原子操作
        // 使用Redis原生命令进行SET NX PX操作
        // 返回是否成功获取锁
        return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            // 键
            byte[] lockKeyBytes = lockKey.getBytes(StandardCharsets.UTF_8);
            // 值
            byte[] lockValueBytes = lockId.getBytes(StandardCharsets.UTF_8);
            // 使用Redis原生命令进行SET NX PX操作
            Boolean acquired = connection.set(
                    lockKeyBytes,
                    lockValueBytes,
                    Expiration.milliseconds(lockExpireTime),
                    RedisStringCommands.SetOption.SET_IF_ABSENT
            );
            // 返回是否成功获取锁
            return acquired != null && acquired;
        }));
    }

    /**
     * 释放锁
     * 只有锁的持有者才能释放锁
     */
    public boolean releaseLock(String lockId) {
        if(StrUtil.isEmpty(lockKey) || StrUtil.isEmpty(lockId)){
            throw new RuntimeException("锁的Key和Value不能为空");
        }
        // 使用Lua脚本确保删除锁时的原子性
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        // 通过execute方法执行Lua脚本，确保只有锁的持有者可以释放锁
        Long result = redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.eval(
                        luaScript.getBytes(),
                        ReturnType.INTEGER,
                        1,
                        lockKey.getBytes(StandardCharsets.UTF_8),
                        lockId.getBytes(StandardCharsets.UTF_8)
                )
        );
        // 返回是否成功释放锁
        return result != null && result == 1;
    }
}
