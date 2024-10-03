package com.redis.interfaces;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Redis消息监听器接口类
 */
public class RedisMessageListener {

    private final MessageListenerAdapter listener;

    private final ChannelTopic topic;

    public RedisMessageListener(RedisMessageHandler handler, String topic){
        this.listener = new MessageListenerAdapter(handler);
        this.topic = new ChannelTopic(topic);
    }

    /**
     * 获取监听器
     */
    public MessageListenerAdapter getListener(){
        listener.afterPropertiesSet();
        return listener;
    }


    /**
     * 获取频道
     */
    public ChannelTopic getTopic(){
        return topic;
    }
}
