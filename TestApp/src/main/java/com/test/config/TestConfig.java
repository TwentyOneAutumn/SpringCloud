package com.test.config;

import com.database.multiDataSource.DataSourceTemplate;
import com.database.multiDataSource.MultiDataSourceTemplate;
import com.database.utils.DataSourceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.List;

@Configuration
public class TestConfig {

    @Bean
    public DataSource Test1DataSource(){
        return DataSourceUtils.mysql("124.221.27.253","test1","root","2762581@com");
    }

    @Bean
    public DataSource Test2DataSource(){
        return DataSourceUtils.mysql("124.221.27.253","test2","root","2762581@com");
    }

    @Bean
    public DataSource Test3DataSource(){
        return DataSourceUtils.mysql("124.221.27.253","test3","root","2762581@com");
    }

    /**
     * 配置多数据源模版
     * @return MultiDataSourceTemplate
     */
    @Bean
    public MultiDataSourceTemplate multiDataSourceTemplate(List<DataSource> dataSourceList) {
        MultiDataSourceTemplate multiDataSourceTemplate = MultiDataSourceTemplate.create(dataSourceList);
        multiDataSourceTemplate.build(DataSourceTemplate.create("com.test.mapper.test1"));
        multiDataSourceTemplate.build(DataSourceTemplate.create("com.test.mapper.test2"));
        multiDataSourceTemplate.build(DataSourceTemplate.create("com.test.mapper.test3"));
        return multiDataSourceTemplate;
    }
}
