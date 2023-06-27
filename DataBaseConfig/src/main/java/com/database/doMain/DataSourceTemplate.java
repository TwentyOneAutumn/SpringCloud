package com.database.doMain;

import javax.sql.DataSource;

/**
 * 数据源模版类
 */
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

    public DataSourceTemplate() {
    }

    public DataSourceTemplate(String dataSourceName, String mapperScanPackage, String resourcesPath, DataSource dataSource) {
        this.dataSourceName = dataSourceName;
        this.mapperScanPackage = mapperScanPackage;
        this.resourcesPath = resourcesPath;
        this.dataSource = dataSource;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getMapperScanPackage() {
        return mapperScanPackage;
    }

    public void setMapperScanPackage(String mapperScanPackage) {
        this.mapperScanPackage = mapperScanPackage;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
