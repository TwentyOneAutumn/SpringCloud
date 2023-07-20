package com.rabbitmq.enums;

/**
 * 延时消息常量类
 */
public class DelayEnum {

    /**
     * 消息过期时间
     */
    public static final String TTL = "x-messageInfo-ttl";

    /**
     * 死信交换机名称
     */
    public static final String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * 死信交换机路由Key
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 最大优先级,取值范围 0-255
     */
    public static final String MAX_PRIORITY = "x-max-priority";

    /**
     * 队列长度
     */
    public static final String MAX_LENGTH = "x-max-length";

    /**
     * 队列溢出行为
     */
    public static final String OVERFLOW = "x-overflow";

    /**
     * 删除队列头部的消息
     */
    public static final String DROP_HEAD = "drop-head";

    /**
     * 拒绝新的消息
     */
    public static final String REJECT_PUBLISH = "reject-publish";
}
