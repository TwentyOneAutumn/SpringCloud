package com.collect.appender;

import cn.hutool.json.JSONObject;
import com.collect.domain.LogInfo;
import com.collect.kafka.LogSendClient;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Plugin(name = "LogCollectAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class LogCollectAppender extends AbstractAppender {

    private String module = null;

    private final String TOPIC = "appender-out-0";

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * 构造方法
     */
    protected LogCollectAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }

    @Override
    public void append(LogEvent event) {
        StackTraceElement source = event.getSource();

        LogInfo pojo = new LogInfo();
        // 模块名
        pojo.setModule(module);
        // 日志级别
        pojo.setLevel(event.getLevel().toString());
        // 时间戳
        pojo.setTimestamp(format.format(new Date(event.getTimeMillis())));
        // 类名
        pojo.setClassName(source.getClassName());
        // 方法名
        pojo.setMethodName(source.getMethodName());
        // 行号
        pojo.setLineNumber(source.getLineNumber());
        // 日志信息
        pojo.setMessage(event.getMessage().getFormat());

        // 发送日志信息到Kafka
        LogSendClient.send(TOPIC,new JSONObject(pojo).toString());
    }


    @Deprecated
    @PluginFactory
    public static LogCollectAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("module") String module) {
        LogCollectAppender appender = new LogCollectAppender(name, null, null, false, Property.EMPTY_ARRAY);
        appender.setModule(module);
        return appender;
    }

    private void setModule(String module){
        this.module = module;
    }
}
