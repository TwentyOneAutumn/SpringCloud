package com.datis.domain;

import lombok.Data;

@Data
public class DatisInfo {

    /**
     * 起飞通播号
     */
    private String depOrder;

    /**
     * 降落通播号
     */
    private String arrOrder;

    /**
     * 起飞QNH
     */
    private String depQnh;

    /**
     * 降落QNH
     */
    private String arrQnh;

    /**
     * 起飞通播时间 UTC 国际时格式
     */
    private String utcTime;

    /**
     * 降落通播时间CST 国际时格式
     */
    private String cstTime;

    /**
     * 起飞通播时间 北京时 yyyy-MM-dd hh:mm:ss
     */
    private String sendTime;
}
