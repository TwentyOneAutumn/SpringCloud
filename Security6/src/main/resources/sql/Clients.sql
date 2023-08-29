CREATE TABLE oauth_client_details
(
    client_id               VARCHAR(256) PRIMARY KEY COMMENT '客户端ID',
    resource_ids            VARCHAR(256) COMMENT '资源ID列表',
    client_secret           VARCHAR(256) COMMENT '客户端秘钥',
    scope                   VARCHAR(256) COMMENT '客户端作用域',
    authorized_grant_types  VARCHAR(256) COMMENT '授权类型列表',
    web_server_redirect_uri VARCHAR(256) COMMENT 'Web服务器重定向URI',
    authorities             VARCHAR(256) COMMENT '授权信息',
    access_token_validity   INT COMMENT '访问令牌有效期（秒）',
    refresh_token_validity  INT COMMENT '刷新令牌有效期（秒）',
    additional_information  VARCHAR(4096) COMMENT '附加信息',
    autoapprove             VARCHAR(256) COMMENT '自动批准的作用域'
);
