package com.database.config;

import com.database.doMain.DataSourceTemplate;
import com.database.doMain.MultiDataSourceTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class MultiDataSourceConfig {

    @Bean
    public MultiDataSourceFactory multiDataSourceFactory(MultiDataSourceTemplate multiDataSourceTemplate){
        return new MultiDataSourceFactory(multiDataSourceTemplate);
    }

    @Bean
    public List<MapperScannerConfigurer> mapperScannerConfigurerList(MultiDataSourceTemplate multiDataSourceTemplate){
        List<MapperScannerConfigurer> mapperScannerConfigurerList = new ArrayList<>();
        List<DataSourceTemplate> dataSourceTemplateList = multiDataSourceTemplate.getDataSourceTemplateList();
        for (int i = 0; i < dataSourceTemplateList.size(); i++) {
            DataSourceTemplate dataSourceTemplate = dataSourceTemplateList.get(i);
            String mapperScanPackage = dataSourceTemplate.getMapperScanPackage();
            String sqlSessionFactoryBeanName = dataSourceTemplate.getDataSourceName() + "SqlSessionFactory";
            String sqlSessionTemplateBeanName = dataSourceTemplate.getDataSourceName() + "SqlSessionTemplate";
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage(mapperScanPackage);
            mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
            mapperScannerConfigurer.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
            mapperScannerConfigurerList.add(mapperScannerConfigurer);
        }
        return mapperScannerConfigurerList;
    }

}
