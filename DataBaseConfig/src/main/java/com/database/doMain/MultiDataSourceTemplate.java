package com.database.doMain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.database.config.EnableBeanConfig;
import com.database.config.MultiDataSourceFactory;
import lombok.Data;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@Data
public class MultiDataSourceTemplate {

    /**
     * 数据源模版集合
     */
    List<DataSourceTemplate> dataSourceTemplateList;

    /**
     * 私有化构造方法
     * @param dataSourceTemplateList 数据源模版集合
     */
    private MultiDataSourceTemplate(List<DataSourceTemplate> dataSourceTemplateList) {
        this.dataSourceTemplateList = dataSourceTemplateList;
    }

    /**
     * 构建MultiDataSourceTemplate
     * @param dataSourceTemplateList 数据源模版集合
     * @param factory 多数据源初始化工厂
     * @return MultiDataSourceTemplate
     */
    public static MultiDataSourceTemplate create(List<DataSourceTemplate> dataSourceTemplateList) {
        if(CollUtil.isNotEmpty(dataSourceTemplateList)){
            return new MultiDataSourceTemplate(dataSourceTemplateList);
        }else {
            throw new IllegalArgumentException("create MultiDataSourceTemplate is error. dataSourceTemplateList is not null");
        }
    }
}
