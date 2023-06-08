//package com.core.config;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//@Configuration
//@MapperScan(basePackages = "com.demo.common.mapper.one", sqlSessionFactoryRef = "", sqlSessionTemplateRef = "")
//public class OneDataSourceConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.one")
//    DataSource oneDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource dataSource)throws Exception{
//        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
//        // 设置数据源
//        factory.setDataSource(dataSource);
//        // 设置XML映射扫描
//        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/one/*.xml"));
//        return factory.getObject();
//    }
//
//    @Bean
//    SqlSessionTemplate oneSqlSessionTemplate(@Qualifier("oneSqlSessionFactory") SqlSessionFactory factory){
//        return new SqlSessionTemplate(factory);
//    }
//}
