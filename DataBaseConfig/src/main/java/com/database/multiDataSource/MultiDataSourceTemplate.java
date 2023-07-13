package com.database.multiDataSource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiDataSourceTemplate {

    /**
     * 数据源模版集合
     */
    private List<DataSourceTemplate> dataSourceTemplateList;

//}
//
//    private static Integer index = 0;

//    private List<DataSource> dataSourceList;

    /**
     * 私有化构造方法
     * @param dataSourceList 数据源集合
     */
//    public MultiDataSourceTemplate(List<DataSource> dataSourceList) {
//        this.dataSourceList = dataSourceList;
//    }

    /**
     * 构建MultiDataSourceTemplate
     * @param dataSourceTemplateList 数据源模版集合
     * @param factory 多数据源初始化工厂
     * @return MultiDataSourceTemplate
     */
//    public static MultiDataSourceTemplate create(List<DataSource> dataSourceList) {
//        return new MultiDataSourceTemplate(dataSourceList);
//    }

    /**
     * 添加数据源模版
     * @param dataSourceTemplate 添加数据源模版对象
     */
//    public void build(DataSourceTemplate dataSourceTemplate) {
//        if(BeanUtil.isNotEmpty(dataSourceTemplate)){
//            if(CollUtil.isNotEmpty(dataSourceTemplateList)){
//                String dataSourceName = dataSourceTemplate.getDataSourceName();
//                String mapperScanPackage = dataSourceTemplate.getMapperScanPackage();
//                // 验证DataSourceName是否重复
//                boolean checkDataSourceName = dataSourceTemplateList.stream().noneMatch(item -> item.getDataSourceName().equals(dataSourceName));
//                if(!checkDataSourceName){
//                    throw new IllegalStateException("The DataSource name already exists");
//                }
//                // 验证MapperScanPackage是否重复
//                boolean checkMapperScanPackage = dataSourceTemplateList.stream().noneMatch(item -> item.getMapperScanPackage().equals(mapperScanPackage));
//                if(!checkMapperScanPackage){
//                    throw new IllegalStateException("The MapperScanPackage already exists");
//                }
//            }
//            dataSourceTemplate.setDataSource(dataSourceList.get(index++));
//            dataSourceTemplateList.add(dataSourceTemplate);
//        }else {
//            throw new IllegalStateException("DataSourceTemplate Bean cannot be null");
//        }
//    }
}
