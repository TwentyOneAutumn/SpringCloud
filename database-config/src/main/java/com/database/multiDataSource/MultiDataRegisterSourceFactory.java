package com.database.multiDataSource;

import cn.hutool.core.collection.CollUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Map;

@Configuration
@DependsOn({"dataSourceMap"})
public class MultiDataRegisterSourceFactory implements BeanDefinitionRegistryPostProcessor{

    @Autowired
    private Map<String,DataSourceTemplate> dataSourceMap;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if(CollUtil.isNotEmpty(dataSourceMap)){
            dataSourceMap.forEach((k,v) -> {
                // 创建 DataSource Bean 的定义
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HikariDataSource.class);
                GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
                // 这里可以设置 DataSource 的属性
                definition.getPropertyValues().add("driverClassName", v.getDriverClassName());
                definition.getPropertyValues().add("jdbcUrl", v.getUrl());
                definition.getPropertyValues().add("username", v.getUsername());
                definition.getPropertyValues().add("password", v.getPassword());
                definition.getPropertyValues().add("password", v.getPassword());
                // 注册 DataSource Bean
                registry.registerBeanDefinition(k + "DataSource", definition);
            });
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition map = beanFactory.getBeanDefinition("dataSourceMap");

    }
}
