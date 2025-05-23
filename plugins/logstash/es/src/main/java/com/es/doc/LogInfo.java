package com.es.doc;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.UUID;

@Data
@Document(indexName = "log_info")
public class LogInfo implements Serializable {

    /**
     * 主键ID
     */
    private String id = UUID.randomUUID().toString();

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 时间戳
     */
    private String timestamp;

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
     * 错误类型
     */
    private String errorType;

    /**
     * 错误堆栈信息
     */
    private String stackTrace;
}
