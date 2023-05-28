//package com.core.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.transaction.ChainedTransactionManager;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//import javax.sql.DataSource;
//
///**
// * 事务管理器配置类
// */
//@Component
//public class TransactionManagementConfig {
//
//    @Bean
//    DataSourceTransactionManager oneDataSourceTransactionManager(@Qualifier("oneDataSource") DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 联合事务管理器
//     * 已弃用
//     * @param oneDataSourceTransactionManager 事务管理器
//     * @return Bean
//     */
//    @Bean
//    ChainedTransactionManager chainedDataSourceTransactionManager(@Qualifier("oneDataSourceTransactionManager") DataSourceTransactionManager oneDataSourceTransactionManager){
//        return new ChainedTransactionManager(oneDataSourceTransactionManager);
//    }
//}
