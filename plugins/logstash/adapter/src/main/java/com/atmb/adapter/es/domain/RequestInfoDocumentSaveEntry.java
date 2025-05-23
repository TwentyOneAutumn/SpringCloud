package com.atmb.adapter.es.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestInfoDocumentSaveEntry {

    /**
     * 主键ID
     */
    private String id = UUID.randomUUID().toString();

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 请求返回值
     */
    private String result;

    /**
     * 请求开始时间
     */
    private String startTime;

    /**
     * 请求结束时间
     */
    private String endTime;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 错误堆栈信息
     */
    private String stackTrace;
}
