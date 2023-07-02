package com.database.multiDataSource;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.util.List;

/**
 * 多数据源配置工厂
 */
public class MultiDataSourceFactory implements BeanFactoryPostProcessor {

    /**
     * 数据源模版缓存
     */
    private List<DataSourceTemplate> dataSourceTemplateList;

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
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 循环注册Bean
        for (DataSourceTemplate dataSourceTemplate : dataSourceTemplateList) {

            // 注册SqlSessionFactory
            SqlSessionFactory sessionFactoryBean = registerSqlSessionFactory(dataSourceTemplate,beanFactory);

            // 注册SqlSessionTemplate
            registerSqlSessionTemplate(dataSourceTemplate, sessionFactoryBean, beanFactory);

            // 注册MapperScannerConfigurer
            registerMapperScannerConfigurer(dataSourceTemplate, beanFactory);
           }
    }

    /**
     * 注册SqlSessionFactory
     * @param dataSourceTemplate 数据库模版对象
     * @param beanFactory Bean工厂对象
     * @return SqlSessionFactory
     * @throws Exception 异常
     */
    public SqlSessionFactory registerSqlSessionFactory(DataSourceTemplate dataSourceTemplate,@NotNull ConfigurableListableBeanFactory beanFactory) throws Exception {
        DataSource dataSource = dataSourceTemplate.getDataSource();
        // 注册SqlSessionFactory
        String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionFactorySuffix;
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        factory.setDataSource(dataSource);
        // 设置XML映射扫描
        String resourcesPath = dataSourceTemplate.getResourcesPath();
        if(StrUtil.isNotEmpty(resourcesPath)){
            factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + resourcesPath));
        }
        SqlSessionFactory sqlSessionFactory = factory.getObject();
        beanFactory.registerSingleton(sqlSessionFactoryBeanName, sqlSessionFactory);
        return beanFactory.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class);
    }

    /**
     * 注册SqlSessionTemplate
     * @param dataSourceTemplate 数据库模版对象
     * @param sessionFactory SqlSessionFactory对象
     * @param beanFactory Bean工厂对象
     * @return SqlSessionTemplateBeanName
     */
    public void registerSqlSessionTemplate(DataSourceTemplate dataSourceTemplate,SqlSessionFactory sessionFactory,@NotNull ConfigurableListableBeanFactory beanFactory){
        // 注册SqlSessionTemplate
        String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionTemplateSuffix;
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sessionFactory);
        beanFactory.registerSingleton(sqlSessionTemplateBeanName, sqlSessionTemplate);
        beanFactory.getBean(sqlSessionTemplateBeanName, SqlSessionTemplate.class);
    }

    /**
     * 注册MapperScannerConfigurer
     * @param dataSourceTemplate 数据库模版对象
     * @param beanFactory Bean工厂对象
     */
    public void registerMapperScannerConfigurer(DataSourceTemplate dataSourceTemplate,@NotNull ConfigurableListableBeanFactory beanFactory){
        // 注册MapperScannerConfigurer
        String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionFactorySuffix;
        String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionTemplateSuffix;
        String mapperScannerConfigurerBeanName = dataSourceTemplate.getDataSourceName() + MapperScannerConfigurerSuffix;
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(dataSourceTemplate.getMapperScanPackage());
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
        beanFactory.registerSingleton(mapperScannerConfigurerBeanName, mapperScannerConfigurer);
        MapperScannerConfigurer mapperScannerConfigurerBean = beanFactory.getBean(mapperScannerConfigurerBeanName, MapperScannerConfigurer.class);
        // 手动调用postProcessBeanDefinitionRegistry()方法加载MapperScan
        mapperScannerConfigurerBean.postProcessBeanDefinitionRegistry((BeanDefinitionRegistry)beanFactory);
    }
}
