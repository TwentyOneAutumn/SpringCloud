package com.core.doMain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.level.Level;
import com.core.interfaces.Module;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.web.method.HandlerMethod;

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
    private String service;


    /**
     * 所属模块
     */
    private String module;


    /**
     * 目标类
     */
    private String className;


    /**
     * 目标方法
     */
    private String methodName;


    /**
     * 行号
     */
    private Integer lineNumber;


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


    /**
     * 打印log
     */
    public static void log(String service,String module,String className,String methodName,Integer lineNumber,Level level, String message){
        JSONObject json = new JSONObject();
        json.set("service",service);
        json.set("module",module);
        json.set("className",className);
        json.set("methodName",methodName);
        json.set("lineNumber",lineNumber);
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
        json.set("lineNumber",methodName);
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
     * 提取信息并发送告警日志
     */
    public static void log(Exception ex, HandlerMethod handlerMethod, String applicationName){
        String module = "";
        // 获取方法所在的类
        Class<?> beanType = handlerMethod.getBeanType();
        // 获取类上的@Module注解
        Module moduleAnnotation = beanType.getAnnotation(Module.class);
        if(BeanUtil.isNotEmpty(moduleAnnotation)){
            module = moduleAnnotation.value();
        }
        // 错误信息
        String message = ex.getMessage();
        // 获取栈信息
        StackTraceElement[] stackTrace = ex.getStackTrace();
        // 获取第一个
        StackTraceElement element = stackTrace[0];
        // 获取抛出异常的类名
        String className = element.getClassName();
        // 获取抛出异常的方法名
        String methodName = element.getMethodName();
        // 获取抛出异常的行号
        int lineNumber = element.getLineNumber();
        // 打印Log
        Logstash.log(applicationName,module,className,methodName,lineNumber, Level.ERROR,message);
    }


    /**
     * 提取信息并发送告警日志
     */
    public static void log(Exception ex, HandlerMethod handlerMethod, String applicationName, String message){
        String module = "";
        // 获取方法所在的类
        Class<?> beanType = handlerMethod.getBeanType();
        // 获取类上的@Module注解
        Module moduleAnnotation = beanType.getAnnotation(Module.class);
        if(BeanUtil.isNotEmpty(moduleAnnotation)){
            module = moduleAnnotation.value();
        }
        // 获取栈信息
        StackTraceElement[] stackTrace = ex.getStackTrace();
        // 获取第一个
        StackTraceElement element = stackTrace[0];
        // 获取抛出异常的类名
        String className = element.getClassName();
        // 获取抛出异常的方法名
        String methodName = element.getMethodName();
        // 获取抛出异常的行号
        int lineNumber = element.getLineNumber();
        // 打印Log
        Logstash.log(applicationName,module,className,methodName,lineNumber, Level.ERROR,message);
    }
}
