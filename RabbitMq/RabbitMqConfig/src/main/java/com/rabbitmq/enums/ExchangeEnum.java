package com.rabbitmq.enums;

/**
 * 交换机枚举类
 */
public class ExchangeEnum {

    /**
     * 广播交换机名称
     */
    public static final String WEATHER_FANOUT_EXCHANGE = "fanoutExchange";

    /**
     * 直连交换机名称
     */
    public static final String WEATHER_DIRECT_EXCHANGE = "directExchange";

    /**
     * 主题交换机名称
     */
    public static final String WEATHER_TOPIC_EXCHANGE = "weatherExchange";

    /**
     * 头部交换机名称
     */
    public static final String WEATHER_HEADERS_EXCHANGE = "headersExchange";


    /**
     * 延迟交换机名称
     */
    public static final String WEATHER_DELAY_EXCHANGE = "delayExchange";


    /**
     * 死信交换机名称
     */
    public static final String WEATHER_DEAD_LETTER_EXCHANGE = "deadLetterExchange";
}
