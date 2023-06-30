package com.database.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

/**
 * 数据库工具类
 */
public class DataSourceUtils {

    /**
     * 构建DataSource
     * @param jdbcUrl 数据库连接
     * @param username 用户名
     * @param password 密码
     * @return DataSource
     */
    public static DataSource builder(String driverClassName, String jdbcUrl, String username, String password){
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    /**
     * 构建MysqlDataSource
     * @param ip IP
     * @param dataBaseName 数据库名称
     * @param username 用户名
     * @param password 密码
     * @return DataSource
     */
    public static DataSource mysql(String ip, String dataBaseName, String username, String password){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(joinJdbcUrl(ip,dataBaseName))
                .username(username)
                .password(password)
                .build();
    }

    /**
     * 拼接MysqlURL
     * @param ip IP
     * @param dataBaseName 数据库名称
     * @return URL
     */
    public static String joinJdbcUrl(String ip, String dataBaseName){
        return "jdbc:mysql://" + ip + ":3306/" + dataBaseName + "?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
    }
}
