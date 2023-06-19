package com.gateway.config;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class GatewayNacosServerStatusListener {

    @Autowired
    private ReactiveDiscoveryClient client;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @PostConstruct
    public void init() {
        NamingService namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        List<RouteDefinition> routes = gatewayProperties.getRoutes();
        routes.forEach(route -> {
            String serviceId = route.getId();
            try {
                namingService.subscribe(serviceId, event -> {
//                    Flux<ServiceInstance> instances = client.getInstances(serviceId);
//                    if (BeanUtil.isNotEmpty(instances)) {
//                        log.info("刷新[" + serviceId + "]服务实例成功");
//                    }
                    System.out.println(serviceId);
                });
            } catch (NacosException e) {
                e.printStackTrace();
            }
        });
    }
}
