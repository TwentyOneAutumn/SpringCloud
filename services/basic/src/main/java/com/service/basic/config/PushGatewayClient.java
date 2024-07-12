package com.service.basic.config;

import cn.hutool.core.map.MapUtil;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

import java.util.Map;
import java.util.Set;

public class PushGatewayClient {

    public static void push(String name, String helpMsg, long value,Map<String,String> lableMap) throws Exception {
        // 创建一个指标注册表
        CollectorRegistry registry = new CollectorRegistry();

        // 判空
        if(MapUtil.isEmpty(lableMap)){
            throw new RuntimeException("指标信息不能为空");
        }

        // 处理指标
        Set<String> keySet = lableMap.keySet();
        String[] keyArr = keySet.toArray(new String[0]);
        String[] valueArr = lableMap.values().toArray(new String[0]);

        // 创建Gauge指标
        Gauge jobExecutionGauge = Gauge.build()
                .name(name)
                .help(helpMsg)
                .labelNames(keyArr)
                .register(registry);

        // 设置Gauge值
        jobExecutionGauge.labels(valueArr).set(value);


        // 推送到 PushGateway
        PushGateway pg = new PushGateway("10.211.55.12:9091");
        pg.pushAdd(registry, name);
    }
}
