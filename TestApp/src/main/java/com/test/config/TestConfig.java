package com.test.config;

import com.database.config.MultiDataSourceFactory;
import com.database.doMain.DataSourceTemplate;
import com.database.doMain.MultiDataSourceTemplate;
import com.database.utils.DataSourceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TestConfig {

    @Bean
    public MultiDataSourceTemplate multiDataSourceTemplate(MultiDataSourceFactory factory){
        List<DataSourceTemplate> dataSourceTemplateList = new ArrayList<>();
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test1","com.test.mapper.test1","mapper/test1/*.xml",
                        DataSourceUtils.builder("jdbc:mysql://124.221.27.253:3306/test1?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true","root","2762581@com")
                )
        );
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test2","com.test.mapper.test2","mapper/test2/*.xml",
                        DataSourceUtils.builder("jdbc:mysql://124.221.27.253:3306/test2?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true","root","2762581@com")
                )
        );
        dataSourceTemplateList.add(
                DataSourceTemplate.create("Test3","com.test.mapper.test3","mapper/test3/*.xml",
                        DataSourceUtils.builder("jdbc:mysql://124.221.27.253:3306/test3?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true","root","2762581@com")
                )
        );
        return MultiDataSourceTemplate.create(dataSourceTemplateList,factory);
    }
}
