package com.database.multiDataSource;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 多数据源配置类，负责加载创建一系列多数据依赖对象
 */
@Configuration
public class MultiDataSourceConfig{

    @Autowired
    private Map<String,DataSourceTemplate> dataSourceMap;


    /**
     * 注册MultiDataSourceFactory，添加数据源模版缓存，方便后续注册组件调用
     * @param dataSourceList 数据源对象集合
     * @return MultiDataSourceFactory
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public MultiDataSourceFactory multiDataSourceFactory(List<DataSource> dataSourceList, Map<String,DataSourceTemplate> dataSourceMap){
        return new MultiDataSourceFactory(dataSourceList,dataSourceMap);
    }

    /**
     * 注册多数据联合事务管理器覆盖默认事务管理器
     * @param dataSourceList 数据源集合
     * @return PlatformTransactionManager
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public PlatformTransactionManager platformTransactionManager(List<DataSource> dataSourceList){
        return new ChainedTransactionManager(dataSourceList.stream().map(DataSourceTransactionManager::new).collect(Collectors.toList()).toArray(new DataSourceTransactionManager[]{}));
    }
}