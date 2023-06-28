package com.database.doMain;

import lombok.Data;
import javax.sql.DataSource;

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

    private DataSourceTemplate(String dataSourceName, String mapperScanPackage, String resourcesPath, DataSource dataSource) {
        this.dataSourceName = dataSourceName;
        this.mapperScanPackage = mapperScanPackage;
        this.resourcesPath = resourcesPath;
        this.dataSource = dataSource;
    }

    public static DataSourceTemplate create(String dataSourceName, String mapperScanPackage, String resourcesPath, DataSource dataSource){
        return new DataSourceTemplate(dataSourceName, mapperScanPackage, resourcesPath,dataSource);
    }


}
