//package com.core.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.transaction.ChainedTransactionManager;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 事务管理器配置类
// */
//@Configuration
//public class TransactionManagementConfig {
//
//    /**
//     * 联合事务管理器
//     * 已弃用
//     * @param dataSource 数据源
//     * @return Bean
//     */
//    @Bean
//    PlatformTransactionManager platformTransactionManager(@Qualifier("oneDataSource") DataSource dataSource){
//        return new ChainedTransactionManager(new DataSourceTransactionManager(dataSource));
//    }
//}
