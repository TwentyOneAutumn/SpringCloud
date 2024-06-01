package com.core.doMain;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.level.Level;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.util.Map;

/**
 * Logstash JSON数据工具类
 */
@Slf4j
@Data
public class Logstash {

    /**
     * 服务名称
     */
    @NotBlank(message = "service参数不能为空")
    private String service;


    /**
     * 所属模块
     */
    @NotBlank(message = "module参数不能为空")
    private String module;


    /**
     * 目标类
     */
    @NotBlank(message = "className参数不能为空")
    private String className;


    /**
     * 目标方法
     */
    @NotBlank(message = "methodName参数不能为空")
    private String methodName;


    /**
     * 日志等级
     */
    private String logLevel;


    /**
     * 日志等级
     */
    private Level level = Level.INFO;


    /**
     * 消息内容
     */
    @NotBlank(message = "message参数不能为空")
    private String message;


    private Logstash(){};


    /**
     * 打印log
     */
    public static void log(String service,String module,String className,String methodName,Level level, String message){
        JSONObject json = new JSONObject();
        json.set("service",service);
        json.set("module",module);
        json.set("className",className);
        json.set("methodName",methodName);
        json.set("level",level);
        json.set("message",message);
        switch (level){
            case TRACE:{
                log.trace(json.toString());
                break;
            }
            case DEBUG:{
                log.debug(json.toString());
                break;
            }
            case INFO:{
                log.info(json.toString());
                break;
            }
            case WARN:{
                log.warn(json.toString());
                break;
            }
            case ERROR:{
                log.error(json.toString());
                break;
            }
        }
    }


    /**
     * 打印log
     */
    public static void log(Map<String,Object> param,Level level){
        JSONObject json = new JSONObject(param);
        switch (level){
            case TRACE:{
                log.trace(json.toString());
                break;
            }
            case DEBUG:{
                log.debug(json.toString());
                break;
            }
            case INFO:{
                log.info(json.toString());
                break;
            }
            case WARN:{
                log.warn(json.toString());
                break;
            }
            case ERROR:{
                log.error(json.toString());
                break;
            }
        }
    }


    /**
     * 打印LOG
     */
    public void log(){
        if(StrUtil.isNotEmpty(logLevel)){
            String upperCase = logLevel.toUpperCase();
            switch (upperCase){
                case "TRACE":{
                    this.level = Level.TRACE;
                    break;
                }
                case "DEBUG":{
                    this.level = Level.DEBUG;
                    break;
                }
                case "WARN":{
                    this.level = Level.WARN;
                    break;
                }
                case "ERROR":{
                    this.level = Level.ERROR;
                    break;
                }
            }
        }
        sendLog();
    }


    /**
     * 打印LOG
     */
    private void sendLog(){
        JSONObject json = new JSONObject();
        json.set("service",service);
        json.set("module",module);
        json.set("className",className);
        json.set("methodName",methodName);
        json.set("level",level);
        json.set("message",message);
        switch (level){
            case TRACE:{
                log.trace(json.toString());
                break;
            }
            case DEBUG:{
                log.debug(json.toString());
                break;
            }
            case INFO:{
                log.info(json.toString());
                break;
            }
            case WARN:{
                log.warn(json.toString());
                break;
            }
            case ERROR:{
                log.error(json.toString());
                break;
            }
        }
    }
}
