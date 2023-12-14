package com.database.multiDataSource;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class MultiDataRegisterSourceFactory implements BeanDefinitionRegistryPostProcessor {

    /**
     * DataSourceConfig对象属性driverClassName
     */
    private final String driverClassName = "driverClassName";

    /**
     * DataSourceConfig对象属性jdbcUrl
     */
    private final String jdbcUrl = "jdbcUrl";

    /**
     * DataSourceConfig对象属性username
     */
    private final String username = "username";

    /**
     * DataSourceConfig对象属性password
     */
    private final String password = "password";

    /**
     * MapperScannerConfigurer对象属性basePackage
     */
    private final String basePackage = "basePackage";

    /**
     * MapperScannerConfigurer对象属性sqlSessionFactoryBeanName
     */
    private final String sqlSessionFactoryBeanName = "sqlSessionFactoryBeanName";

    /**
     * MapperScannerConfigurer对象属性sqlSessionTemplateBeanName
     */
    private final String sqlSessionTemplateBeanName = "sqlSessionTemplateBeanName";

    /**
     * DataSourceConfig对象后缀名
     */
    private final String DataSourceConfigSuffix = "DataSourceConfig";

    /**
     * DataSource对象后缀名
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


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registerBean(registry,analysisConfig());
    }

    public void registerBean(BeanDefinitionRegistry registry,Map<String, DataSourceTemplate> dataSourceTemplateMap){
        // 循环注册Bean
        dataSourceTemplateMap.forEach((name,dataSourceTemplate) -> {
            String dataSourceName = registerDataSourceBean(registry, name, dataSourceTemplate);
            String sqlSessionFactoryName = registerSqlSessionFactoryBean(registry, name, dataSourceTemplate, dataSourceName);
            String sqlSessionTemplateBeanName = registerSqlSessionTemplateBean(registry, name, sqlSessionFactoryName);
            registerMapperScannerConfigurerBean(registry,name,dataSourceTemplate,sqlSessionFactoryName,sqlSessionTemplateBeanName);
        });
    }

    public String registerDataSourceBean(BeanDefinitionRegistry registry,String name,DataSourceTemplate dataSourceTemplate){
        String configName = name + DataSourceConfigSuffix;
        String dataSourceName = name + DataSourceSuffix;
        BeanDefinitionBuilder configBuilder = BeanDefinitionBuilder.genericBeanDefinition(HikariConfig.class);
        configBuilder.addPropertyValue(driverClassName,dataSourceTemplate.getDriverClassName())
                .addPropertyValue(jdbcUrl,dataSourceTemplate.getUrl())
                .addPropertyValue(username,dataSourceTemplate.getUsername())
                .addPropertyValue(password,dataSourceTemplate.getPassword());
        registry.registerBeanDefinition(configName,configBuilder.getBeanDefinition());

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HikariDataSource.class);
        builder.addConstructorArgReference(configName);
        registry.registerBeanDefinition(dataSourceName,builder.getBeanDefinition());
        return dataSourceName;
    }

    @SneakyThrows
    public String registerSqlSessionFactoryBean(BeanDefinitionRegistry registry, String name, DataSourceTemplate dataSourceTemplate, String dataSourceName){
        String sqlSessionFactoryName = name + SqlSessionFactorySuffix;
        String environmentName = name + "Environment";
        String mybatisConfigurationName = name + "MybatisConfiguration";
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
//        factory.setGlobalConfig();
        GlobalConfig globalConfig = new GlobalConfig();
        // 设置XML映射扫描
        String xmlScan = dataSourceTemplate.getXmlScan();
        if(StrUtil.isNotEmpty(xmlScan)){
//            factory.setGlobalConfig();
            factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:" + xmlScan));
        }

        BeanDefinitionBuilder environmentBuilder = BeanDefinitionBuilder.genericBeanDefinition(Environment.class);
        environmentBuilder.addConstructorArgValue(environmentName)
                .addConstructorArgValue(new SpringManagedTransactionFactory())
                .addConstructorArgReference(dataSourceName);
        registry.registerBeanDefinition(environmentName,environmentBuilder.getBeanDefinition());

        BeanDefinitionBuilder mybatisConfigurationBuilder = BeanDefinitionBuilder.genericBeanDefinition(MybatisConfiguration.class);
        mybatisConfigurationBuilder.addConstructorArgReference(environmentName)
                .addPropertyValue("logImpl",org.apache.ibatis.logging.stdout.StdOutImpl.class)
//                .addPropertyValue("globalConfig",new GlobalConfig().setBanner(false))
                .addPropertyValue("databaseId",dataSourceName);
        if(StrUtil.isNotEmpty(xmlScan)){
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:" + xmlScan);
            mybatisConfigurationBuilder.addPropertyValue("loadedResources",Arrays.stream(resources).map(Resource::toString).collect(Collectors.toSet()));
        }
        registry.registerBeanDefinition(mybatisConfigurationName,mybatisConfigurationBuilder.getBeanDefinition());


        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultSqlSessionFactory.class);
        builder.addConstructorArgReference(mybatisConfigurationName);
        registry.registerBeanDefinition(sqlSessionFactoryName,builder.getBeanDefinition());
        return sqlSessionFactoryName;
    }

    public String registerSqlSessionTemplateBean(BeanDefinitionRegistry registry,String name,String sqlSessionFactoryName){
        String sqlSessionTemplateName = name + SqlSessionTemplateSuffix;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionTemplate.class);
        builder.addConstructorArgReference(sqlSessionFactoryName);
        registry.registerBeanDefinition(sqlSessionTemplateName,builder.getBeanDefinition());
        return sqlSessionTemplateName;
    }

    public void registerMapperScannerConfigurerBean(BeanDefinitionRegistry registry,String name,DataSourceTemplate dataSourceTemplate,String sqlSessionFactoryBeanName,String sqlSessionTemplateBeanName){
        String mapperScannerConfigurerName = name + MapperScannerConfigurerSuffix;
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        builder.addPropertyValue(basePackage,dataSourceTemplate.getMapperScan())
                .addPropertyValue("processPropertyPlaceHolders",true)
                .addPropertyValue("beanName",mapperScannerConfigurerName)
                .addPropertyValue(this.sqlSessionFactoryBeanName,sqlSessionFactoryBeanName)
                .addPropertyValue(this.sqlSessionTemplateBeanName,sqlSessionTemplateBeanName);
        registry.registerBeanDefinition(mapperScannerConfigurerName,builder.getBeanDefinition());
    }

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

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
