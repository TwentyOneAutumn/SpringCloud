package com.collect.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class LogInfo implements Serializable {

    /**
     * 主键ID
     */
    private String id = UUID.randomUUID().toString();

    /**
     * 模块名称
     */
    private String module;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 日志级别
     */
    private String timestamp;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 行号
     */
    private Integer lineNumber;

    /**
     * 日志信息
     */
    private String message;
}
