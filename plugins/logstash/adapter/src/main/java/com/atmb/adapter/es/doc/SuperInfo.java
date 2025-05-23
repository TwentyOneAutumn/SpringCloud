package com.atmb.adapter.es.doc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Data
public class SuperInfo {

    /**
     * 主键ID
     */
    @Id
    private String id = UUID.randomUUID().toString();

    /**
     * 模块名称
     */
    @Field(type = FieldType.Text)
    private String module;

    /**
     * 日志级别
     */
    @Field(type = FieldType.Date)
    private String timestamp;
}
