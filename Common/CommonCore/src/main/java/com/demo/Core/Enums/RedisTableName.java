package com.demo.Core.Enums;

/**
 * 配置Redis缓存表名枚举类
 */
public class RedisTableName {
    // 用户表
    public static final String USER_TABLE_NAME = "";

    // 用户与部门中间表
    public static final String USER_DEPARTMENT_TABLE_NAME = "";

    // 部门表
    public static final String DEPARTMENT_TABLE_NAME = "";

    // 扇区表
    public static final String SECTOR_TABLE_NAME = "";

    // 终端表
    public static final String POSITION_TABLE_NAME = "";

    // 字典表
    public static final String DATA_DICTIONARY_TABLE_NAME = "";

    // 角色表
    public static final String ROLE_TABLE_NAME = "";

    // 消息表
    public static final String MSG_TABLE_NAME = "";

    // 菜单表
    public static final String MENU_TABLE_NAME = "";

    // 所有表数组
    public static final String[] TABLE_NAME_ARR = {
            USER_TABLE_NAME,
            USER_DEPARTMENT_TABLE_NAME,
            DEPARTMENT_TABLE_NAME,
            SECTOR_TABLE_NAME,
            POSITION_TABLE_NAME,
            DATA_DICTIONARY_TABLE_NAME,
            ROLE_TABLE_NAME,
            MSG_TABLE_NAME,
            MENU_TABLE_NAME
    };
}
