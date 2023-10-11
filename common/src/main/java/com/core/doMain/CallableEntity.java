package com.core.doMain;

import lombok.Data;

@Data
public class CallableEntity<T> {

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务结果
     */
    private T returnValue;

    /**
     * 任务执行时间
     * 单位：秒
     */
    private long executionTime;

    /**
     * 是否异常
     */
    private boolean isError;

    /**
     * 异常对象
     */
    private Exception exception;

    public CallableEntity(String name, T returnValue, long executionTime, boolean isError) {
        this.name = name;
        this.returnValue = returnValue;
        this.executionTime = executionTime;
        this.isError = isError;
    }

    public CallableEntity(String name, long executionTime, boolean isError, Exception exception) {
        this.name = name;
        this.executionTime = executionTime;
        this.isError = isError;
        this.exception = exception;
    }
}
