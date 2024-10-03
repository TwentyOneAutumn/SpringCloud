package com.redis.interfaces;

/**
 * Redis消息处理器接口类
 */
public interface RedisMessageHandler {

    /**
     * 处理接收到的消息
     * @param message 消息内容
     * @param channel 频道
     */
    void handleMessage(String message, String channel);
}
