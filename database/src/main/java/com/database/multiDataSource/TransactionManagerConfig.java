package com.database.multiDataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多数据源配置类，负责加载创建一系列多数据依赖对象
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class TransactionManagerConfig {

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
