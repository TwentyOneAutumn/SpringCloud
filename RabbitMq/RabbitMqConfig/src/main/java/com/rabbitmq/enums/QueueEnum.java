package com.rabbitmq.enums;

/**
 * 队列枚举类
 */
public class QueueEnum {

    /**
     * 天气广播消息对列
     */
    public static final String WEATHER_FANOUT_QUEUE = "weatherFanoutQueue";


    /**
     * 天气直连消息对列
     */
    public static final String WEATHER_DIRECT_QUEUE = "weatherDirectQueue";


    /**
     * 天气主题消息对列
     */
    public static final String WEATHER_TOPIC_QUEUE = "weatherTopicQueue";


    /**
     * 天气头部消息对列
     */
    public static final String WEATHER_HEADERS_QUEUE = "weatherHeadersQueue";



    /**
     * 天气延迟队列
     */
    public static final String WEATHER_DELAY_QUEUE = "weatherDelayQueue";



    /**
     * 天气死信队列
     */
    public static final String WEATHER_DEAD_LETTER_QUEUE = "weatherDeadLetterQueue";


    /**
     * 天气手动ACK对列
     */
    public static final String WEATHER_MANUAL_ACK_QUEUE = "weatherManualAckQueue";
}
