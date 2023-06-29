package com.database.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.database.doMain.DataSourceTemplate;
import com.database.doMain.MultiDataSourceTemplate;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 多数据源配置工厂
 */
public class MultiDataSourceFactory implements BeanFactoryPostProcessor {


    private List<DataSourceTemplate> dataSourceTemplateList;

    /**
     * 数据源对象后缀名
     */
    private final String DataSourceSuffix = "DataSource";

    /**
     * SqlSessionFactory对象后缀名
     */
    private final String SqlSessionFactorySuffix = "SqlSessionFactory";

    /**
     * SqlSessionTemplate对象后缀名
     */
    private final String SqlSessionTemplateSuffix = "SqlSessionTemplate";

    /**
     * MapperScannerConfigurer对象后缀名
     */
    private final String MapperScannerConfigurerSuffix = "MapperScannerConfigurer";


    public MultiDataSourceFactory(MultiDataSourceTemplate multiDataSourceTemplate){
        this.dataSourceTemplateList = multiDataSourceTemplate.getDataSourceTemplateList();
    }

    @Override
    @SneakyThrows
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 循环注册Bean
        for (DataSourceTemplate dataSourceTemplate : dataSourceTemplateList) {
            DataSource dataSource = dataSourceTemplate.getDataSource();

            // 注册SqlSessionFactory
            String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionFactorySuffix;
            MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
            // 设置数据源
            factory.setDataSource(dataSource);
            // 设置XML映射扫描
//        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/one/*.xml"));
            SqlSessionFactory sqlSessionFactory = factory.getObject();
            beanFactory.registerSingleton(sqlSessionFactoryBeanName, sqlSessionFactory);

            // 注册SqlSessionTemplate
            SqlSessionFactory sessionFactoryBean = beanFactory.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class);
            String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionTemplateSuffix;
            SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactoryBean);
            beanFactory.registerSingleton(sqlSessionTemplateBeanName, sqlSessionTemplate);

            // 注册MapperScannerConfigurer
            String mapperScannerConfigurerBeanName = dataSourceTemplate.getDataSourceName() + MapperScannerConfigurerSuffix;
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage(dataSourceTemplate.getMapperScanPackage());
            mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
            mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
            beanFactory.registerSingleton(mapperScannerConfigurerBeanName, mapperScannerConfigurer);
            MapperScannerConfigurer mapperScannerConfigurerBean = beanFactory.getBean(mapperScannerConfigurerBeanName, MapperScannerConfigurer.class);
        }
    }
}
