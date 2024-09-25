package com.service.websocket.config;

import com.redis.topic.RedisTopic;
import com.redis.interfaces.RedisMessageHandler;
import com.redis.interfaces.RedisMessageListener;
import com.service.websocket.redis.MessageInformHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类
 */
@Configuration
public class WebSocketConfig {

    /**
     * 用于将使用 @ServerEndpoint 注解标记的 WebSocket 处理程序注册到应用程序上下文中
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 消息通知处理器
     */
    @Bean
    public RedisMessageHandler messageInformHandler(){
        return new MessageInformHandler();
    }

    /**
     * 消息通知监听器
     */
    @Bean
    public RedisMessageListener redisMessageListener(RedisMessageHandler messageInformHandler){
        return new RedisMessageListener(messageInformHandler, RedisTopic.INFORM);
    }
}
