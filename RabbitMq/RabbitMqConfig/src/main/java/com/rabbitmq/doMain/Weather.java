package com.rabbitmq.doMain;

import lombok.Data;

@Data
public class Weather {

    /**
     * 地区
     */
    private String address;

    /**
     * 天气情况
     */
    private String weather;
}
