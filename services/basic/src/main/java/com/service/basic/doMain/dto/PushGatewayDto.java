package com.service.basic.doMain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PushGatewayDto {

    /**
     * 指标名称
     */
    private String name;

    /**
     * 帮助信息
     */
    private String helpMsg;

    /**
     * 标签
     */
    private Map<String,String> lableMap;
}
