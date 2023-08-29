package com.security.enums;

/**
 * 客户端查询Sql枚举类
 */
public class ClientsSql {
    public static final String SELECT_CLIENT_DETAILS_SQL = "select * from oauth_client_details where client_id = ?";
    public static final String FIND_CLIENT_DETAILS_SQL = "select * from oauth_client_details";
}
