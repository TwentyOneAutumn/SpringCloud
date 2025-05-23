package com.atmb.adapter.es.doc;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.serializer.Deserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "log_info")
public class LogInfo extends SuperInfo implements Serializable {

    /**
     * 日志级别
     */
    @Field(type = FieldType.Text)
    private String level;

    /**
     * 类名
     */
    @Field(type = FieldType.Text)
    private String className;

    /**
     * 方法名
     */
    @Field(type = FieldType.Text)
    private String methodName;

    /**
     * 行号
     */
    @Field(type = FieldType.Text)
    private Integer lineNumber;

    /**
     * 日志信息
     */
    @Field(type = FieldType.Text)
    private String message;
}
