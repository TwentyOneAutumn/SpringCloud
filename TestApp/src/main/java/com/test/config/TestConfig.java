package com.test.config;

import com.database.config.MultiDataSourceFactory;
import com.database.doMain.DataSourceTemplate;
import com.database.doMain.MultiDataSourceTemplate;
import com.database.utils.DataSourceUtils;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfig {


    @Bean
    public DataSource Test1DataSource(){
        return DataSourceUtils.builder(DataSourceUtils.joinJdbcUrl("124.221.27.253","test1"),"root","2762581@com");
    }

    @Bean
    public DataSource Test2DataSource(){
        return DataSourceUtils.builder(DataSourceUtils.joinJdbcUrl("124.221.27.253","test2"),"root","2762581@com");
    }

    @Bean
    public DataSource Test3DataSource(){
        return DataSourceUtils.builder(DataSourceUtils.joinJdbcUrl("124.221.27.253","test3"),"root","2762581@com");
    }


    @Bean
    public MultiDataSourceTemplate multiDataSourceTemplate(DataSource Test1DataSource, DataSource Test2DataSource, DataSource Test3DataSource){
        List<DataSourceTemplate> dataSourceTemplateList = new ArrayList<>();
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test1","com.test.mapper.test1","mapper/test1/*.xml", Test1DataSource)
        );
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test2","com.test.mapper.test2","mapper/test2/*.xml", Test2DataSource)
        );
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test3","com.test.mapper.test3","mapper/test3/*.xml", Test3DataSource)
        );
        return MultiDataSourceTemplate.create(dataSourceTemplateList);
    }

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer1(){
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("com.test.mapper.test1");
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("Test1SqlSessionFactory");
//        mapperScannerConfigurer.setSqlSessionTemplateBeanName("Test1SqlSessionTemplate");
//        return mapperScannerConfigurer;
//    }
}
