package com.database.utils;

import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

/**
 * 数据库工具类
 */
public class DataSourceUtils {

    /**
     * 构建MysqlDataSource
     * @param jdbcUrl 数据库连接
     * @param username 用户名
     * @param password 密码
     * @return DataSource
     */
    public static DataSource builder(String jdbcUrl, String username, String password){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }
}
