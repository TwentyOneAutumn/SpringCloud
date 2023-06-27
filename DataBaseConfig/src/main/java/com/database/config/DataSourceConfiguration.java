package com.database.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.database.doMain.DataSourceTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 多数据源配置
 */
public class DataSourceConfiguration {

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
     * Spring上下文对象
     */
    private final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(EnableBeanConfig.class);

    /**
     * SpringBean工厂对象
     */
    private final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getParentBeanFactory();

    private List<DataSourceTransactionManager> transactionManagerList;

    /**
     * 读取配置文件中的数据源信息
     * @return 数据源信息集合
     */
    @Bean
    @ConfigurationProperties(prefix = "data-source-collection")
    public List<DataSourceTemplate> dataSourceCollection() {
        return new ArrayList<>();
    }

    @PostConstruct
    private void registerDataSourceBean(List<DataSourceTemplate> dataSourceCollection) throws Exception {
        for (DataSourceTemplate dataSourceTemplate : dataSourceCollection) {
            String dataSourceBeanName = dataSourceTemplate.getDataSourceName() + DataSourceSuffix;

            // 注册数据源
            DataSource dataSource = registerBean(dataSourceBeanName, dataSourceTemplate.getDataSource(), DataSource.class);

            // 注册SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = registerSqlSessionFactoryBean(dataSourceTemplate, dataSource);

            // 注册SqlSessionTemplate
            String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionTemplateSuffix;
            registerBean(sqlSessionTemplateBeanName, new SqlSessionTemplate(sqlSessionFactory),SqlSessionTemplate.class);

            // 创建数据源对应事务管理器并存储
            transactionManagerList.add(new DataSourceTransactionManager(dataSource));
        }
        // 注册联合事务管理器
        PlatformTransactionManager platformTransactionManager = new ChainedTransactionManager(transactionManagerList.toArray(new DataSourceTransactionManager[]{}));
        registerBean("platformTransactionManager", platformTransactionManager,PlatformTransactionManager.class);
    }

    /**
     * 将SqlSessionFactory注册到Spring容器
     * @param dataSourceTemplate 数据源模版对象
     * @param dataSource 数据源对象
     * @return 注册的SqlSessionFactory Bean
     */
    private SqlSessionFactory registerSqlSessionFactoryBean(DataSourceTemplate dataSourceTemplate, DataSource dataSource) throws Exception {
        // 注册SqlSessionFactory
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        // 设置数据源
        factory.setDataSource(dataSource);
        // 设置Mapper扫描
        factory.setTypeAliasesPackage(dataSourceTemplate.getMapperScanPackage());
        // 设置XML映射扫描
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + dataSourceTemplate.getResourcesPath()));
        SqlSessionFactory sqlSessionFactory = factory.getObject();
        String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionFactorySuffix;
        return registerBean(sqlSessionFactoryBeanName,sqlSessionFactory,SqlSessionFactory.class);
    }

    /**
     * 将对象注册到Spring容器
     * @param beanName 对象在Spring容器中的名称
     * @param bean Bean对象
     * @param tClass 对象类型
     * @return 注册的Bean
     */
    private <T> T registerBean(String beanName, Object bean,Class<T> tClass) {
        // 注册Bean
        beanFactory.registerSingleton(beanName, bean);
        // 获取手动注入的Bean实例
        return applicationContext.getBean(beanName,tClass);
    }
}
