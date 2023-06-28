package com.database.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.database.doMain.DataSourceTemplate;
import com.database.doMain.MultiDataSourceTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 多数据源配置工厂
 */
public class MultiDataSourceFactory {

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
    private final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

    /**
     * 事务管理器缓存列表
     */
//    private List<DataSourceTransactionManager> transactionManagerList;


    public void registerDataSourceBean(MultiDataSourceTemplate multiDataSourceTemplate) throws Exception {
        if(BeanUtil.isNotEmpty(multiDataSourceTemplate)){
            List<DataSourceTemplate> dataSourceTemplateList = multiDataSourceTemplate.getDataSourceTemplateList();
            if(CollUtil.isNotEmpty(dataSourceTemplateList)){
                List<DataSourceTransactionManager> transactionManagerList = new ArrayList<>();
                // 循环注册Bean
                for (DataSourceTemplate dataSourceTemplate : dataSourceTemplateList) {
                    String dataSourceBeanName = dataSourceTemplate.getDataSourceName() + DataSourceSuffix;

                    // 注册数据源
                    DataSource dataSource = registerBean(dataSourceBeanName, dataSourceTemplate.getDataSource());

                    // 注册SqlSessionFactory
                    SqlSessionFactory sqlSessionFactory = registerSqlSessionFactoryBean(dataSourceTemplate, dataSource);

                    // 注册SqlSessionTemplate
                    String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionTemplateSuffix;
                    registerBean(sqlSessionTemplateBeanName, new SqlSessionTemplate(sqlSessionFactory));

                    // 创建数据源对应事务管理器并存储
                    transactionManagerList.add(new DataSourceTransactionManager(dataSource));
                }
                // 注册联合事务管理器
                PlatformTransactionManager platformTransactionManager = new ChainedTransactionManager(transactionManagerList.toArray(new DataSourceTransactionManager[]{}));
                registerBean("platformTransactionManager", platformTransactionManager);
            }
        }
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
//        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + dataSourceTemplate.getResourcesPath()));
        SqlSessionFactory sqlSessionFactory = factory.getObject();
        String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + SqlSessionFactorySuffix;
        return registerBean(sqlSessionFactoryBeanName,sqlSessionFactory);
    }

    /**
     * 将对象注册到Spring容器
     * @param beanName 对象在Spring容器中的名称
     * @param bean Bean对象
     * @return 注册的Bean
     */
    private DataSource registerBean(String beanName, DataSource bean) {
        // 注册Bean
        beanFactory.registerSingleton(beanName, bean);
        // 获取手动注入的Bean实例
        return getBean(beanName, bean.getClass());
    }


    /**
     * 将对象注册到Spring容器
     * @param beanName 对象在Spring容器中的名称
     * @param bean Bean对象
     * @return 注册的Bean
     */
    private SqlSessionFactory registerBean(String beanName, SqlSessionFactory bean) {
        // 注册Bean
        beanFactory.registerSingleton(beanName, bean);
        // 获取手动注入的Bean实例
        return getBean(beanName, bean.getClass());
    }


    /**
     * 将对象注册到Spring容器
     * @param beanName 对象在Spring容器中的名称
     * @param bean Bean对象
     * @return 注册的Bean
     */
    private SqlSessionTemplate registerBean(String beanName, SqlSessionTemplate bean) {
        // 注册Bean
        beanFactory.registerSingleton(beanName, bean);
        // 获取手动注入的Bean实例
        return getBean(beanName, bean.getClass());
    }

    /**
     * 将对象注册到Spring容器
     * @param beanName 对象在Spring容器中的名称
     * @param bean Bean对象
     * @return 注册的Bean
     */
    private PlatformTransactionManager registerBean(String beanName, PlatformTransactionManager bean) {
        // 注册Bean
        beanFactory.registerSingleton(beanName, bean);
        // 获取手动注入的Bean实例
        return getBean(beanName, bean.getClass());
    }

    /**
     * 从Spring容器获取Bean
     * @param beanName Bean名称
     * @param tClass Bean类型
     * @return Bean
     * @param <T> 泛型
     */
    private <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName,tClass);
    }
}
