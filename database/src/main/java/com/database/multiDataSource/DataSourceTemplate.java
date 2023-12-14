package com.database.multiDataSource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源模版类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceTemplate {

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
     * 包扫描
     */
    private String mapperScan;

    /**
     * xml包扫描
     */
    private String xmlScan;
}
