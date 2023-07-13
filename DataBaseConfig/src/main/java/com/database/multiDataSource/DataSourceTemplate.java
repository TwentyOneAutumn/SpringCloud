package com.database.multiDataSource;

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
     * 驱动
     */
    private String driverClassName;

    /**
     * url
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据源
     */
    private DataSource dataSource;

//    private DataSourceTemplate(String dataSourceName, String mapperScanPackage, String resourcesPath) {
//        this.dataSourceName = dataSourceName;
//        this.mapperScanPackage = mapperScanPackage;
//        this.resourcesPath = resourcesPath;
//    }
//
//    private DataSourceTemplate(String dataSourceName, String mapperScanPackage) {
//        this.dataSourceName = dataSourceName;
//        this.mapperScanPackage = mapperScanPackage;
//    }
//
//    public static DataSourceTemplate create(String mapperScanPackage, String resourcesPath){
//        if(StrUtil.isEmpty(mapperScanPackage)){
//            throw new IllegalStateException("MapperScanPackage Bean cannot be null");
//        }
//        return new DataSourceTemplate(createDataSourceName(), mapperScanPackage, resourcesPath);
//    }
//
//    public static DataSourceTemplate create(String mapperScanPackage){
//        if(StrUtil.isEmpty(mapperScanPackage)){
//            throw new IllegalStateException("MapperScanPackage Bean cannot be null");
//        }
//        return new DataSourceTemplate(createDataSourceName(), mapperScanPackage);
//    }
//
//    /**
//     * 获取DataSource名称
//     * @return DataSource名称
//     */
//    public static String createDataSourceName(){
//        return "DataSource" + new Date().getTime();
//    }
}
