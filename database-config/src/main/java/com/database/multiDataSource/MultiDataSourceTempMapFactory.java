package com.database.multiDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiDataSourceTempMapFactory {

    @Bean(name = "dataSourceMap")
    @ConfigurationProperties(prefix = "multi.datasource")
    public Map<String, DataSourceTemplate> dataSourceMap(){
        return new HashMap<>();
    }
}
