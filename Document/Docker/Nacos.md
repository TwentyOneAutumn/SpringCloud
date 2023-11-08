# Docker deploy Nacos

### Nacos

1. 拉取Nacos镜像

   ```shell
   docker pull nacos/nacos-server:v2.2.2
   ```

2. 初始化MySQL

   ```sql
   SET NAMES utf8mb4;
   SET FOREIGN_KEY_CHECKS = 0;
   
   -- ----------------------------
   -- Table structure for config_info
   -- ----------------------------
   DROP TABLE IF EXISTS `config_info`;
   CREATE TABLE `config_info` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'group_id',
     `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
     `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
     `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
     `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
     `c_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'configuration description',
     `c_use` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'configuration usage',
     `effect` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '配置生效的描述',
     `type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT '配置的类型',
     `c_schema` text COLLATE utf8mb3_bin COMMENT '配置的模式',
     `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info';
   
   -- ----------------------------
   -- Records of config_info
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for config_info_aggr
   -- ----------------------------
   DROP TABLE IF EXISTS `config_info_aggr`;
   CREATE TABLE `config_info_aggr` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
     `datum_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
     `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
     `gmt_modified` datetime NOT NULL COMMENT '修改时间',
     `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='增加租户字段';
   
   -- ----------------------------
   -- Records of config_info_aggr
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for config_info_beta
   -- ----------------------------
   DROP TABLE IF EXISTS `config_info_beta`;
   CREATE TABLE `config_info_beta` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
     `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
     `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
     `beta_ips` varchar(1024) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
     `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
     `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
     `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_beta';
   
   -- ----------------------------
   -- Records of config_info_beta
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for config_info_tag
   -- ----------------------------
   DROP TABLE IF EXISTS `config_info_tag`;
   CREATE TABLE `config_info_tag` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
     `tag_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
     `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
     `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
     `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
     `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_info_tag';
   
   -- ----------------------------
   -- Records of config_info_tag
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for config_tags_relation
   -- ----------------------------
   DROP TABLE IF EXISTS `config_tags_relation`;
   CREATE TABLE `config_tags_relation` (
     `id` bigint NOT NULL COMMENT 'id',
     `tag_name` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
     `tag_type` varchar(64) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
     `nid` bigint NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
     PRIMARY KEY (`nid`),
     UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
     KEY `idx_tenant_id` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='config_tag_relation';
   
   -- ----------------------------
   -- Records of config_tags_relation
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for group_capacity
   -- ----------------------------
   DROP TABLE IF EXISTS `group_capacity`;
   CREATE TABLE `group_capacity` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
     `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
     `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
     `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
     `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
     `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
     `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_group_id` (`group_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='集群、各Group容量信息表';
   
   -- ----------------------------
   -- Records of group_capacity
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for his_config_info
   -- ----------------------------
   DROP TABLE IF EXISTS `his_config_info`;
   CREATE TABLE `his_config_info` (
     `id` bigint unsigned NOT NULL COMMENT 'id',
     `nid` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
     `data_id` varchar(255) COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
     `group_id` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
     `app_name` varchar(128) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
     `content` longtext COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
     `md5` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     `src_user` text COLLATE utf8mb3_bin COMMENT 'source user',
     `src_ip` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
     `op_type` char(10) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'operation type',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
     `encrypted_data_key` text COLLATE utf8mb3_bin NOT NULL COMMENT '密钥',
     PRIMARY KEY (`nid`),
     KEY `idx_gmt_create` (`gmt_create`),
     KEY `idx_gmt_modified` (`gmt_modified`),
     KEY `idx_did` (`data_id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='多租户改造';
   
   -- ----------------------------
   -- Records of his_config_info
   -- ----------------------------
   BEGIN;
   INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (0, 1, 'test1', 'DEFAULT_GROUP', '', 'test: 1', '0a18b0fb516e42becfb68564ce650d8f', '2023-09-19 07:09:19', '2023-09-19 15:09:20', NULL, '183.62.13.66', 'I', '', '');
   INSERT INTO `his_config_info` (`id`, `nid`, `data_id`, `group_id`, `app_name`, `content`, `md5`, `gmt_create`, `gmt_modified`, `src_user`, `src_ip`, `op_type`, `tenant_id`, `encrypted_data_key`) VALUES (1, 2, 'test1', 'DEFAULT_GROUP', '', 'test: 1', '0a18b0fb516e42becfb68564ce650d8f', '2023-09-19 07:09:34', '2023-09-19 15:09:35', NULL, '183.62.13.66', 'D', '', '');
   COMMIT;
   
   -- ----------------------------
   -- Table structure for permissions
   -- ----------------------------
   DROP TABLE IF EXISTS `permissions`;
   CREATE TABLE `permissions` (
     `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'role',
     `resource` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'resource',
     `action` varchar(8) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'action',
     UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
   
   -- ----------------------------
   -- Records of permissions
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for roles
   -- ----------------------------
   DROP TABLE IF EXISTS `roles`;
   CREATE TABLE `roles` (
     `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'username',
     `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'role',
     UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
   
   -- ----------------------------
   -- Records of roles
   -- ----------------------------
   BEGIN;
   INSERT INTO `roles` (`username`, `role`) VALUES ('nacos', 'ROLE_ADMIN');
   COMMIT;
   
   -- ----------------------------
   -- Table structure for tenant_capacity
   -- ----------------------------
   DROP TABLE IF EXISTS `tenant_capacity`;
   CREATE TABLE `tenant_capacity` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
     `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
     `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
     `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
     `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
     `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
     `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
     `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_tenant_id` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='租户容量信息表';
   
   -- ----------------------------
   -- Records of tenant_capacity
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for tenant_info
   -- ----------------------------
   DROP TABLE IF EXISTS `tenant_info`;
   CREATE TABLE `tenant_info` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
     `kp` varchar(128) COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
     `tenant_id` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
     `tenant_name` varchar(128) COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
     `tenant_desc` varchar(256) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
     `create_source` varchar(32) COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
     `gmt_create` bigint NOT NULL COMMENT '创建时间',
     `gmt_modified` bigint NOT NULL COMMENT '修改时间',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
     KEY `idx_tenant_id` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='tenant_info';
   
   -- ----------------------------
   -- Records of tenant_info
   -- ----------------------------
   BEGIN;
   COMMIT;
   
   -- ----------------------------
   -- Table structure for users
   -- ----------------------------
   DROP TABLE IF EXISTS `users`;
   CREATE TABLE `users` (
     `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'username',
     `password` varchar(500) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'password',
     `enabled` tinyint(1) NOT NULL COMMENT 'enabled',
     PRIMARY KEY (`username`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
   
   -- ----------------------------
   -- Records of users
   -- ----------------------------
   BEGIN;
   INSERT INTO `users` (`username`, `password`, `enabled`) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
   COMMIT;
   
   SET FOREIGN_KEY_CHECKS = 1;
   ```

2. 单机启动

   ```shell
   docker run -d \
   -p 8848:8848 \
   -p 9848:9848 \
   -p 9849:9849 \
   -e MODE=standalone \
   -e MYSQL_SERVICE_HOST=124.221.27.253 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_DB_NAME=nacos \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=2762581@com \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   --restart=always \
   --name nacos \
   nacos/nacos-server:v2.2.2
   ```

3. 集群启动

   ```shell
   docker run -d \
   -p 8848:8848 \
   -p 9848:9848 \
   -p 9849:9849 \
   -e MODE=cluster \
   -e NACOS_SERVER_IP=124.221.27.253 \
   -e NACOS_SERVERS=124.221.27.253:8848,124.221.27.253:8849,124.221.27.253:8850 \
   -e NACOS_APPLICATION_PORT=8848 \
   -e MYSQL_SERVICE_HOST=124.221.27.253 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_DB_NAME=nacos \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=2762581@com \
   -e PREFER_HOST_MODE=hostname \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   --restart=always  \
   --name nacos1  \
   nacos/nacos-server:v2.2.2
   ```

   ```shell
   	docker run -d \
   -p 8849:8849 \
   -p 9850:9850 \
   -p 9851:9851 \
   -e MODE=cluster \
   -e NACOS_SERVER_IP=124.221.27.253 \
   -e NACOS_SERVERS=124.221.27.253:8848,124.221.27.253:8849,124.221.27.253:8850 \
   -e NACOS_APPLICATION_PORT=8849 \
   -e MYSQL_SERVICE_HOST=124.221.27.253 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_DB_NAME=nacos \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=2762581@com \
   -e PREFER_HOST_MODE=hostname \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   --restart=always  \
   --name nacos1  \
   nacos/nacos-server:v2.2.2
   ```

   ```shell
   docker run -d \
   -p 8850:8850 \
   -p 9852:9852 \
   -p 9853:9853 \
   -e MODE=cluster \
   -e NACOS_SERVER_IP=124.221.27.253 \
   -e NACOS_SERVERS=124.221.27.253:8848,124.221.27.253:8849,124.221.27.253:8850 \
   -e NACOS_APPLICATION_PORT=8850 \
   -e MYSQL_SERVICE_HOST=124.221.27.253 \
   -e MYSQL_SERVICE_PORT=3306 \
   -e MYSQL_SERVICE_DB_NAME=nacos \
   -e MYSQL_SERVICE_USER=root \
   -e MYSQL_SERVICE_PASSWORD=2762581@com \
   -e PREFER_HOST_MODE=hostname \
   -e SPRING_DATASOURCE_PLATFORM=mysql \
   --restart=always  \
   --name nacos3  \
   nacos/nacos-server:v2.2.2
   ```

4. 开启集群权限（未测试）

   ```shell
   -e NACOS_AUTH_ENABLE=true \
   -e NACOS_AUTH_IDENTITY_KEY=nacos \
   -e NACOS_AUTH_IDENTITY_VALUE=nacos \
   -e NACOS_AUTH_TOKEN='VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=' \
   ```

   

---

