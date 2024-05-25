package com.database.multiDataSource;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 多数据源注册工厂类
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MultiDataRegisterSourceFactory implements BeanDefinitionRegistryPostProcessor {

    /**
     * 缓存BeanDefinitionRegistry
     */
    private BeanDefinitionRegistry registry;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 获取BeanDefinitionRegistry
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 注册多数剧院相关Bean
        registerBean(beanFactory,analysisConfig());
    }

    /**
     * 注册多数据源相关Bean
     * @param beanFactory bean工厂对象
     * @param dataSourceTemplateMap 数据源模版集合
     */
    public void registerBean(ConfigurableListableBeanFactory beanFactory,Map<String, DataSourceTemplate> dataSourceTemplateMap){
        // 循环注册Bean
        dataSourceTemplateMap.forEach((name,dataSourceTemplate) -> {
            DataSource dataSource = registerDataSourceBean(beanFactory, name, dataSourceTemplate);
            SqlSessionFactory sqlSessionFactory = registerSqlSessionFactoryBean(beanFactory, name, dataSourceTemplate, dataSource);
            registerSqlSessionTemplateBean(beanFactory, name, sqlSessionFactory);
            registerMapperScannerConfigurerBean(beanFactory,name,dataSourceTemplate);
        });
    }

    /**
     * 注册DataSource Bean
     * @param beanFactory bean工厂对象
     * @param name 数据源名称
     * @param dataSourceTemplate 数据源模版对象
     * @return DataSource
     */
    public DataSource registerDataSourceBean(ConfigurableListableBeanFactory beanFactory,String name,DataSourceTemplate dataSourceTemplate){
        String dataSourceName = MultiDataSourceSuffix.joinDataSourceSuffix(name);
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(dataSourceTemplate.getDriverClassName())
                .url(dataSourceTemplate.getUrl())
                .username(dataSourceTemplate.getUsername())
                .password(dataSourceTemplate.getPassword())
                .build();
        beanFactory.registerSingleton(dataSourceName,dataSource);
        return dataSource;
    }

    /**
     * 注册SqlSessionFactory Bean
     * @param beanFactory bean工厂对象
     * @param name 数据源名称
     * @param dataSourceTemplate 数据源模版对象
     * @param dataSource 数据源对象
     * @return SqlSessionFactory
     */
    @SneakyThrows
    public SqlSessionFactory registerSqlSessionFactoryBean(ConfigurableListableBeanFactory beanFactory, String name, DataSourceTemplate dataSourceTemplate, DataSource dataSource){
        String sqlSessionFactoryBeanName = MultiDataSourceSuffix.joinSqlSessionFactorySuffix(name);
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        factory.setGlobalConfig(globalConfig);
        // 设置XML映射扫描
        String xmlScan = dataSourceTemplate.getXmlScan();
        if(StrUtil.isNotEmpty(xmlScan)){
            factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + xmlScan));
        }
        factory.setDataSource(dataSource);
        SqlSessionFactory sessionFactory = factory.getObject();
        beanFactory.registerSingleton(sqlSessionFactoryBeanName,sessionFactory);
        return sessionFactory;
    }

    /**
     * 注册SqlSessionTemplate Bean
     * @param beanFactory bean工厂对象
     * @param name 数据源名称
     * @param sqlSessionFactory SqlSessionFactory对象
     */
    public void registerSqlSessionTemplateBean(ConfigurableListableBeanFactory beanFactory,String name,SqlSessionFactory sqlSessionFactory){
        String sqlSessionTemplateBeanName = MultiDataSourceSuffix.joinSqlSessionTemplateSuffix(name);
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        beanFactory.registerSingleton(sqlSessionTemplateBeanName,sqlSessionTemplate);
    }

    /**
     * 注册MapperScannerConfigurer Bean
     * @param beanFactory bean工厂对象
     * @param name 数据源名称
     * @param dataSourceTemplate 数据源模版对象
     */
    public void registerMapperScannerConfigurerBean(ConfigurableListableBeanFactory beanFactory,String name,DataSourceTemplate dataSourceTemplate){
        String mapperScannerConfigurerBeanName = MultiDataSourceSuffix.joinMapperScannerConfigurerSuffix(name);
        String sqlSessionFactoryBeanName = MultiDataSourceSuffix.joinSqlSessionFactorySuffix(name);
        String sqlSessionTemplateBeanName = MultiDataSourceSuffix.joinSqlSessionTemplateSuffix(name);
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
        mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
        mapperScannerConfigurer.setBasePackage(dataSourceTemplate.getMapperScan());
        mapperScannerConfigurer.postProcessBeanDefinitionRegistry(this.registry);
        beanFactory.registerSingleton(mapperScannerConfigurerBeanName,mapperScannerConfigurer);
    }

    /**
     * 获取多数据源配置
     * @return Map
     */
    @SneakyThrows
    public Map<String,DataSourceTemplate> analysisConfig(){
        Map<String,DataSourceTemplate> dataSourceMap = new HashMap<>();

        // 读取application.yaml中的内容
        YamlPropertiesFactoryBean applicationYamlFactory = new YamlPropertiesFactoryBean();
        applicationYamlFactory.setResources(new ClassPathResource("application.yml"));
        Properties applicationProperties = applicationYamlFactory.getObject();
        Map<String, DataSourceTemplate> applicationMap = analysisProperties(applicationProperties);
        if(CollUtil.isEmpty(applicationMap)){
            String applicationName = applicationProperties.getProperty("spring.application.name");
            // 读取bootstrap.yml文件中的内容
            YamlPropertiesFactoryBean bootstrapYamlFactory = new YamlPropertiesFactoryBean();
            bootstrapYamlFactory.setResources(new ClassPathResource("bootstrap.yml"));
            Properties bootstrapProperties = bootstrapYamlFactory.getObject();
            Map<String, DataSourceTemplate>  bootstrapMap = analysisProperties(applicationProperties);
            if(CollUtil.isEmpty(bootstrapMap)){
                // 读取nacos配置中心中配置文件的内容
                String nacosAddr = bootstrapProperties.getProperty("spring.cloud.nacos.config.server-addr");
                if(StrUtil.isNotEmpty(nacosAddr)){
                    String nacosNameSpace = bootstrapProperties.getProperty("spring.cloud.nacos.config.namespace");
                    Map<String,String> map = new HashMap<>();
                    String applicationDataId = null;
                    int index = 0;
                    while (true){
                        String dataId = bootstrapProperties.getProperty("spring.cloud.nacos.config.shared-configs[" + index + "].dataId");
                        if(StrUtil.isNotEmpty(dataId)){
                            String group = bootstrapProperties.getProperty("spring.cloud.nacos.config.shared-configs[" + index + "].group");
                            if(StrUtil.isNotEmpty(group)){
                                map.put(dataId,group);
                            }
                            map.put(dataId,"DEFAULT_GROUP");
                        }else {
                            break;
                        }
                        index++;
                    }
                    if(CollUtil.isNotEmpty(map)){
                        Set<String> keySet = map.keySet();
                        for (String dataId : keySet) {
                            if(dataId.contains(applicationName)){
                                applicationDataId = dataId;
                                break;
                            }
                        }
                    }
                    if(StrUtil.isNotEmpty(applicationDataId)){
                        OkHttpClient client = new OkHttpClient();
                        StringBuilder builder = new StringBuilder();
                        builder.append("http://").append(nacosAddr).append("/nacos/v1/cs/configs?")
                                .append("tenant=").append(nacosNameSpace).append("&")
                                .append("dataId=").append(applicationName).append("&")
                                .append("group=").append(map.get(applicationDataId));
                        // 创建请求对象
                        Request request = new Request.Builder()
                                .url(builder.toString())
                                .build();
                        // 执行请求
                        Response response = client.newCall(request).execute();
                        // 获取响应码
                        int statusCode = response.code();
                        if(statusCode == 200){
                            String responseBody = response.body().string();
                            if(StrUtil.isNotEmpty(responseBody)){
                                YamlPropertiesFactoryBean nacosYamlFactory = new YamlPropertiesFactoryBean();
                                nacosYamlFactory.setResources(new ByteArrayResource(responseBody.getBytes()));
                                Properties nacosProperties = applicationYamlFactory.getObject();
                                Map<String, DataSourceTemplate> naoosMap = analysisProperties(nacosProperties);
                                dataSourceMap = naoosMap;
                            }
                        }
                    }
                }
            }else {
                dataSourceMap = bootstrapMap;
            }
        }else {
            dataSourceMap = applicationMap;
        }
        return dataSourceMap;
    }

    /**
     * 解析配置,提取数据源模版
     * @param properties 配置对象
     * @return Map
     */
    public Map<String,DataSourceTemplate> analysisProperties(Properties properties){
        Map<String,DataSourceTemplate> map = new HashMap<>();
        int index = 0;
        while (true){
            String name = properties.getProperty("multi.datasource[" + index + "].name");
            String driverClassName = properties.getProperty("multi.datasource[" + index + "].driver-class-name");
            String url = properties.getProperty("multi.datasource[" + index + "].url");
            String username = properties.getProperty("multi.datasource[" + index + "].username");
            String password = properties.getProperty("multi.datasource[" + index + "].password");
            String mapperScan = properties.getProperty("multi.datasource[" + index + "].mapper-scan");
            String xmlScan = properties.getProperty("multi.datasource[" + index + "].xml-scan");
            int isError = 0;
            List<String> errorMsg = new ArrayList<>();
            if(StrUtil.isEmpty(name)){
                isError++;
                errorMsg.add("multi.datasource." + name);
            }
            if(StrUtil.isEmpty(driverClassName)){
                isError++;
                errorMsg.add("multi.datasource." + driverClassName);
            }
            if(StrUtil.isEmpty(url)){
                isError++;
                errorMsg.add("multi.datasource." + url);
            }
            if(StrUtil.isEmpty(username)){
                isError++;
                errorMsg.add("multi.datasource." + username);
            }
            if(StrUtil.isEmpty(password)){
                isError++;
                errorMsg.add("multi.datasource." + password);
            }
            if(StrUtil.isEmpty(mapperScan)){
                isError++;
                errorMsg.add("[multi.datasource." + mapperScan + "]");
            }
            if(isError == 0){
                map.put(name,new DataSourceTemplate(driverClassName,url,username,password,mapperScan,xmlScan));
                index++;
            }else if(isError < 6){
                throw new RuntimeException("The " + String.join(",",errorMsg) + " property in the configuration cannot be empty");
            }else {
                break;
            }
        }
        return map;
    }

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