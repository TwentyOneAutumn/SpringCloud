package com.executor.domain;

import cn.hutool.log.level.Level;
import com.core.doMain.Logstash;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class ExecutorState {

    /**
     * 任务名称
     */
    private String executorName;


    /**
     * 任务频率
     */
    private String executorFrequency;


    /**
     * 任务开始时间
     */
    private LocalDateTime startTime;


    /**
     * 任务结束时间
     */
    private LocalDateTime endTime;


    /**
     * 任务状态
     */
    private Boolean executorStatus = true;


    /**
     * 目标类
     */
    private String errorClassName;


    /**
     * 目标方法
     */
    private String errorMethodName;


    /**
     * 行号
     */
    private Integer errorLineNumber;


    /**
     * 错误信息
     */
    private String errorMessage;


    /**
     * 创建对象
     */
    public static ExecutorState create(String executorName, String executorFrequency) {
        ExecutorState executorState = new ExecutorState();
        executorState.setExecutorName(executorName);
        executorState.setExecutorFrequency(executorFrequency);
        executorState.setStartTime(LocalDateTime.now());
        return executorState;
    }


    /**
     * 设置异常信息
     */
    public void setError(String errorClassName, String errorMethodName, Integer errorLineNumber, String errorMessage) {
        this.executorStatus = false;
        this.endTime = LocalDateTime.now();
        this.errorClassName = errorClassName;
        this.errorMethodName = errorMethodName;
        this.errorLineNumber = errorLineNumber;
        this.errorMessage = errorMessage;
    }


    /**
     * 打印日志
     */
    public void log(){
        Map<String, Object> paramMap = new LinkedHashMap<>();
        paramMap.put("executorName", executorName);
        paramMap.put("executorFrequency", executorFrequency);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", executorFrequency);
        paramMap.put("executorStatus", executorStatus ? "成功" : "失败");
        paramMap.put("errorClassName", errorClassName);
        paramMap.put("errorMethodName", errorMethodName);
        paramMap.put("errorLineNumber", errorLineNumber);
        paramMap.put("errorMessage", errorMessage);
        Logstash.log(paramMap,this.executorStatus ? Level.INFO : Level.ERROR);
    }
}
