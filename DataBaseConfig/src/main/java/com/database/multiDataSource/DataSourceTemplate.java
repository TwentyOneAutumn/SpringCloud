package com.database.multiDataSource;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import javax.sql.DataSource;
import java.util.Date;

/**
 * 数据源模版类
 */
@Data
public class DataSourceTemplate {

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 包扫描路径
     */
    private String mapperScanPackage;

    /**
     * XML资源路径
     */
    private String resourcesPath;

    /**
     * 数据源
     */
    private DataSource dataSource;

    private DataSourceTemplate(String dataSourceName, String mapperScanPackage, String resourcesPath) {
        this.dataSourceName = dataSourceName;
        this.mapperScanPackage = mapperScanPackage;
        this.resourcesPath = resourcesPath;
    }

    private DataSourceTemplate(String dataSourceName, String mapperScanPackage) {
        this.dataSourceName = dataSourceName;
        this.mapperScanPackage = mapperScanPackage;
    }

    public static DataSourceTemplate create(String mapperScanPackage, String resourcesPath){
        if(StrUtil.isEmpty(mapperScanPackage)){
            throw new IllegalStateException("MapperScanPackage Bean cannot be null");
        }
        return new DataSourceTemplate(createDataSourceName(), mapperScanPackage, resourcesPath);
    }

    public static DataSourceTemplate create(String mapperScanPackage){
        if(StrUtil.isEmpty(mapperScanPackage)){
            throw new IllegalStateException("MapperScanPackage Bean cannot be null");
        }
        return new DataSourceTemplate(createDataSourceName(), mapperScanPackage);
    }

    /**
     * 获取DataSource名称
     * @return DataSource名称
     */
    public static String createDataSourceName(){
        return "DataSource" + new Date().getTime();
    }
}
