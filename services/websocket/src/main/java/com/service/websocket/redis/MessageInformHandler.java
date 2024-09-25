package com.service.websocket.redis;

import com.redis.interfaces.RedisMessageHandler;
import com.service.websocket.endpoint.WebSocketEndpoint;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息通知处理器
 */
@Slf4j
public class MessageInformHandler implements RedisMessageHandler {

    @Override
    public void handleMessage(String message, String channel) {
        // 给所有连接WebSocket的客户端发送消息
        WebSocketEndpoint.sendMessageToAll(message);
    }
}
