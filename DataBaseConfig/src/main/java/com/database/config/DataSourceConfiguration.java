package com.database.config;

import com.database.doMain.DataSourceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class DataSourceConfiguration {

    @Autowired
    private BeanDefinitionRegistry beanDefinitionRegistry;


    @Bean
    @ConfigurationProperties(prefix = "data-source-collection")
    public List<DataSourceTemplate> dataSourceCollection() {
        return new ArrayList<>();
    }

    /**
     * 将数据源注册为BeanDefinition
     */
    @PostConstruct
    public void registerBeans(List<DataSourceTemplate> dataSourceCollection) {
        // 读取数据源
        dataSourceCollection.forEach(dataSourceTemplate -> {
            // 注册数据源
            registerBean(dataSourceTemplate.getDataSourceName(),dataSourceTemplate.getDataSource());
            // 注册SqlSessionFactory

            // 注册SqlSessionTemplate
        });
    }

    /**
     * 将对象注册为BeanDefinition
     * @param beanName 对象在Spring容器中的名称
     * @param bean 对象
     */
    private void registerBean(String beanName, Object bean) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(bean.getClass());
        beanDefinition.setInstanceSupplier(() -> bean);
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}
