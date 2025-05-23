package com.es.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
@Document(indexName = "request_info")
public class RequestInfo {

    /**
     * 主键ID
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * 服务名称
     */
    @Field(type = FieldType.Text)
    private String serviceName;

    /**
     * 时间戳
     */
    @Field(type = FieldType.Date)
    private String timestamp;

    /**
     * 请求类型
     */
    @Field(type = FieldType.Text)
    private String requestType;

    /**
     * 请求IP地址
     */
    @Field(type = FieldType.Text)
    private String ip;

    /**
     * 请求URL
     */
    @Field(type = FieldType.Text)
    private String url;

    /**
     * 请求参数
     */
    @Field(type = FieldType.Text)
    private String param;

    /**
     * 请求返回值
     */
    @Field(type = FieldType.Text)
    private String result;

    /**
     * 请求开始时间
     */
    @Field(type = FieldType.Date)
    private String startTime;

    /**
     * 请求结束时间
     */
    @Field(type = FieldType.Date)
    private String endTime;

    /**
     * 错误类型
     */
    @Field(type = FieldType.Text)
    private String errorType;

    /**
     * 错误堆栈信息
     */
    @Field(type = FieldType.Text)
    private String stackTrace;
}
