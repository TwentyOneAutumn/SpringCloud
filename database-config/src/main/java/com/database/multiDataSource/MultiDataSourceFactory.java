package com.database.multiDataSource;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 多数据源配置工厂
 */
public class MultiDataSourceFactory implements BeanFactoryPostProcessor {

    /**
     * 数据源模版缓存
     */
    private List<DataSource> dataSourceList;

    /**
     * 数据源模版Map
     */
    private Map<String,DataSourceTemplate> dataSourceMap;

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

    public MultiDataSourceFactory(List<DataSource> dataSourceList, Map<String,DataSourceTemplate> dataSourceMap){
        this.dataSourceList = dataSourceList;
        this.dataSourceMap = dataSourceMap;
    }

    @Override
    @SneakyThrows
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 循环注册Bean
        for (DataSource dataSource : dataSourceList) {

            String dataSourceName = dataSource.getClass().getName().replace("DataSource", "");
            DataSourceTemplate dataSourceTemplate = dataSourceMap.get(dataSourceName);

            // 注册SqlSessionFactory
            SqlSessionFactory sessionFactoryBean = registerSqlSessionFactory(dataSource,dataSourceName,dataSourceTemplate.getXmlScan(),beanFactory);

            // 注册SqlSessionTemplate
            registerSqlSessionTemplate(dataSourceName, sessionFactoryBean, beanFactory);

            // 注册MapperScannerConfigurer
            registerMapperScannerConfigurer(dataSourceName,dataSourceTemplate.getMapperScan(),beanFactory);
        }
    }

    /**
     * 注册SqlSessionFactory
     *
     * @param dataSourceName
     * @param beanFactory        Bean工厂对象
     * @return SqlSessionFactory
     * @throws Exception 异常
     */
    public SqlSessionFactory registerSqlSessionFactory(DataSource dataSource, String dataSourceName, String xmlScan, @NotNull ConfigurableListableBeanFactory beanFactory) throws Exception {
        // 注册SqlSessionFactory
        String sqlSessionFactoryBeanName = dataSourceName + SqlSessionFactorySuffix;
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        factory.setDataSource(dataSource);
        // 设置XML映射扫描
        if(StrUtil.isNotEmpty(xmlScan)){
            factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + xmlScan));
        }
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        factory.setConfiguration(mybatisConfiguration);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        factory.setGlobalConfig(globalConfig);

        SqlSessionFactory sqlSessionFactory = factory.getObject();
        beanFactory.registerSingleton(sqlSessionFactoryBeanName, sqlSessionFactory);
        return beanFactory.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class);
    }

    /**
     * 注册SqlSessionTemplate
     *
     * @param dataSourceName
     * @param sessionFactory     SqlSessionFactory对象
     * @param beanFactory        Bean工厂对象
     * @return SqlSessionTemplateBeanName
     */
    public SqlSessionTemplate registerSqlSessionTemplate(String dataSourceName, SqlSessionFactory sessionFactory, @NotNull ConfigurableListableBeanFactory beanFactory){
        // 注册SqlSessionTemplate
        String sqlSessionTemplateBeanName = dataSourceName + SqlSessionTemplateSuffix;
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory);
        beanFactory.registerSingleton(sqlSessionTemplateBeanName, sqlSessionTemplate);
        return beanFactory.getBean(sqlSessionTemplateBeanName, SqlSessionTemplate.class);
    }

    /**
     * 注册MapperScannerConfigurer
     *
     * @param dataSourceTemplate 数据库模版对象
     * @param dataSourceName
     * @param beanFactory        Bean工厂对象
     */
    /**
     * 注册MapperScannerConfigurer
     * @param dataSourceName
     * @param mapperScan
     * @param beanFactory
     */
    public void registerMapperScannerConfigurer(String dataSourceName, String mapperScan,@NotNull ConfigurableListableBeanFactory beanFactory){
        // 注册MapperScannerConfigurer
        String sqlSessionFactoryBeanName = dataSourceName + SqlSessionFactorySuffix;
        String sqlSessionTemplateBeanName = dataSourceName + SqlSessionTemplateSuffix;
        String mapperScannerConfigurerBeanName = dataSourceName + MapperScannerConfigurerSuffix;
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(mapperScan);
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);

        MapperFactoryBean mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setSqlSessionFactory(beanFactory.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class));
        mapperFactoryBean.setSqlSessionTemplate(beanFactory.getBean(sqlSessionTemplateBeanName, SqlSessionTemplate.class));
        mapperScannerConfigurer.setMapperFactoryBeanClass(mapperFactoryBean.getClass());
        beanFactory.registerSingleton(mapperScannerConfigurerBeanName, mapperScannerConfigurer);
        MapperScannerConfigurer mapperScannerConfigurerBean = beanFactory.getBean(mapperScannerConfigurerBeanName, MapperScannerConfigurer.class);
        // 手动调用postProcessBeanDefinitionRegistry()方法加载MapperScan
        mapperScannerConfigurerBean.postProcessBeanDefinitionRegistry((BeanDefinitionRegistry)beanFactory);
    }
}