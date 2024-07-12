package com.database.multiDataSource;

/**
 * 多数据源后缀名枚举类
 */
public class MultiDataSourceSuffix {

    /**
     * DataSource对象后缀名
     */
    public static final String DataSourceSuffix = "DataSource";

    /**
     * SqlSessionFactory对象后缀名
     */
    public static final String SqlSessionFactorySuffix = "SqlSessionFactory";

    /**
     * SqlSessionTemplate对象后缀名
     */
    public static final String SqlSessionTemplateSuffix = "SqlSessionTemplate";

    /**
     * MapperScannerConfigurer对象后缀名
     */
    public static final String MapperScannerConfigurerSuffix = "MapperScannerConfigurer";

    public static String joinDataSourceSuffix(String name){
        return name + DataSourceSuffix;
    }

    public static String joinSqlSessionFactorySuffix(String name){
        return name + SqlSessionFactorySuffix;
    }

    public static String joinSqlSessionTemplateSuffix(String name){
        return name + SqlSessionTemplateSuffix;
    }

    public static String joinMapperScannerConfigurerSuffix(String name){
        return name + MapperScannerConfigurerSuffix;
    }
}
