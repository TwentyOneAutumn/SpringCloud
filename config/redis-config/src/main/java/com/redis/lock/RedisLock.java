package com.redis.lock;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;


public class RedisLock {

    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 锁的名称
     */
    private final String lockKey;

    /**
     * 锁的名称
     */
    private final String lockId;

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
    private RedisLock(RedisTemplate<String,Object> redisTemplate, String lockKey, String lockId, long lockExpireTime) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.lockId = lockId;
        this.lockExpireTime = lockExpireTime * 1000;
    }

    /**
     * 构建锁对象
     * @param redisTemplate redisTemplate
     * @param lockKey 锁名称
     * @param lockId 锁密匙
     * @param lockExpireTime 锁过期时间
     * @return RedisLock
     */
    public static RedisLock builder(RedisTemplate<String,Object> redisTemplate, String lockKey, String lockId, long lockExpireTime){
        return new RedisLock(redisTemplate,lockKey,lockId,lockExpireTime);
    }

    /**
     * 构建锁对象
     * @param redisTemplate redisTemplate
     * @param lockKey 锁名称
     * @param lockExpireTime 锁过期时间
     * @return RedisLock
     */
    public static RedisLock builder(RedisTemplate<String,Object> redisTemplate, String lockKey, long lockExpireTime){
        return new RedisLock(redisTemplate,lockKey,UUID.randomUUID().toString(),lockExpireTime);
    }

    /**
     * 构建锁对象
     * @param redisTemplate redisTemplate
     * @param lockKey 锁名称
     * @param lockId 锁密匙
     * @return RedisLock
     */
    public static RedisLock builder(RedisTemplate<String,Object> redisTemplate, String lockKey, String lockId){
        return new RedisLock(redisTemplate,lockKey,lockId,60000);
    }

    /**
     * 构建锁对象
     * @param redisTemplate redisTemplate
     * @param lockKey 锁名称
     * @return RedisLock
     */
    public static RedisLock builder(RedisTemplate<String,Object> redisTemplate, String lockKey){
        return new RedisLock(redisTemplate,lockKey,UUID.randomUUID().toString(),60000);
    }

    /**
     * 获取指定的锁
     * @return 是否获取到锁
     */
    public boolean acquireLock() {
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
    public boolean releaseLock() {
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
