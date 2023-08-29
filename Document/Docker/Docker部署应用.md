# Docker部署应用



---



### Nacos

1. 拉取Nacos镜像

   ```shell
   docker pull nacos/nacos-server:v2.2.2
   ```

2. 单机启动

   ```shell
   docker run -d \
   -p 8848:8848 \
   -e MODE=standalone \
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



### Redis

1. 拉取Redis镜像 

   ```shell
   docker pull redis:7.0
   ```

1. 单机启动

   ```shell
   docker run -d \
   -p 6379:6379 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-master \
   --restart=always \ 
   redis:7.0 \
   redis-server /home/redis/config/redis.conf \
   --port 6379 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --dir /home/redis/data \
   --appendfilename RedisMasterAof.aof
   ```

1. 集群启动

   ```shell
   docker run -d \
   -p 6379:6379 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-master \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6379 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --dir /home/redis/data \
   --appendfilename RedisMasterAof.aof
   ```

   ```shell
   docker run -d \
   -p 6380:6380 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-slave1 \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6380 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --slaveof redis-master 6379
   --dir /home/redis/data \
   --appendfilename RedisSlaveOneAof.aof
   ```

   ```shell
   docker run -d \
   -p 6381:6381 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-slave2 \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6381 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --slaveof redis-master 6379
   --dir /home/redis/data \
   --appendfilename RedisSlaveTwoAof.aof
   ```

   ```shell
   docker run -d \
   -p 6379:6379 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel1 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26379 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   ```shell
   docker run -d \
   -p 26380:26380 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel2 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26380 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   ```shell
   docker run -d \
   -p 26381:26381 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel3 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26381 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   

---



### Seata

1. 拉取Seata镜像 

   ```shell
   docker pull seataio/seata-server:1.6.0
   ```

2. 启动容器

   ```shell
   docker run --rm -d --name seata -p 8091:8091 -p 7091:7091 seataio/seata-server:1.6.0
   ```

3. Copy resources 目录

   ```shell
   docker cp seata:/seata-server/resources /home/seata/config
   ```

4. 切换到目录下编辑文件application.yml

   ```shell
   cd /home/seata/config/resources
   vim application.yml
   ```

   复制并粘贴以下内容

   ```yaml
   server:
     port: 7091
   spring:
     application:
       name: seata
   logging:
     config: classpath:logback-spring.xml
     file:
       path: ${user.home}/logs/seata
     extend:
       logstash-appender:
         destination: 127.0.0.1:4560
       kafka-appender:
         bootstrap-servers: 124.221.27.253:9092
         topic: seataLog
   console:
     user:
       username: seata
       password: seata
   seata:
     config: # 配置中心
       type: nacos
       nacos:
         server-addr: 124.221.27.253:8848 # Nacos地址
         group: SEATA_GROUP # 分组
         namespace: 38efd505-cc1a-4ffa-b1fb-d8aa0982e8b2 # 命名空间
         username: nacos
         password: nacos
         data-id: SeataConfig.yaml
     registry: # 注册中心
       type: nacos
       nacos:
         server-addr: 124.221.27.253:8848
         group: SEATA_GROUP # 分组
         namespace: 38efd505-cc1a-4ffa-b1fb-d8aa0982e8b2 # 命名空间
         username: nacos
         password: nacos
     store: # 存储方式
       mode: db # 数据库存储
       db:
         datasource: druid
         dbType: mysql
         driverClassName: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://124.221.27.253:3306/seata?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
         user: root
         password: 2762581@com
         minConn: 5
         maxConn: 100
         globalTable: global_table
         branchTable: branch_table
         lockTable: lock_table
         queryLimit: 100
         maxWait: 5000
     security:
       secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
       tokenValidityInMilliseconds: 1800000
       ignore:
         urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
   ```

5. 创建对应Mysql数据库，并在数据库下创建seata依赖表

   ```sql
   -- -------------------------------- The script used when storeMode is 'db' --------------------------------
   -- the table to store GlobalSession data
   CREATE TABLE IF NOT EXISTS `global_table`
   (
       `xid`                       VARCHAR(128) NOT NULL,
       `transaction_id`            BIGINT,
       `status`                    TINYINT      NOT NULL,
       `application_id`            VARCHAR(32),
       `transaction_service_group` VARCHAR(32),
       `transaction_name`          VARCHAR(128),
       `timeout`                   INT,
       `begin_time`                BIGINT,
       `application_data`          VARCHAR(2000),
       `gmt_create`                DATETIME,
       `gmt_modified`              DATETIME,
       PRIMARY KEY (`xid`),
       KEY `idx_status_gmt_modified` (`status` , `gmt_modified`),
       KEY `idx_transaction_id` (`transaction_id`)
   ) ENGINE = InnoDB
     DEFAULT CHARSET = utf8mb4;
   
   -- the table to store BranchSession data
   CREATE TABLE IF NOT EXISTS `branch_table`
   (
       `branch_id`         BIGINT       NOT NULL,
       `xid`               VARCHAR(128) NOT NULL,
       `transaction_id`    BIGINT,
       `resource_group_id` VARCHAR(32),
       `resource_id`       VARCHAR(256),
       `branch_type`       VARCHAR(8),
       `status`            TINYINT,
       `client_id`         VARCHAR(64),
       `application_data`  VARCHAR(2000),
       `gmt_create`        DATETIME(6),
       `gmt_modified`      DATETIME(6),
       PRIMARY KEY (`branch_id`),
       KEY `idx_xid` (`xid`)
   ) ENGINE = InnoDB
     DEFAULT CHARSET = utf8mb4;
   
   -- the table to store lock data
   CREATE TABLE IF NOT EXISTS `lock_table`
   (
       `row_key`        VARCHAR(128) NOT NULL,
       `xid`            VARCHAR(128),
       `transaction_id` BIGINT,
       `branch_id`      BIGINT       NOT NULL,
       `resource_id`    VARCHAR(256),
       `table_name`     VARCHAR(32),
       `pk`             VARCHAR(36),
       `status`         TINYINT      NOT NULL DEFAULT '0' COMMENT '0:locked ,1:rollbacking',
       `gmt_create`     DATETIME,
       `gmt_modified`   DATETIME,
       PRIMARY KEY (`row_key`),
       KEY `idx_status` (`status`),
       KEY `idx_branch_id` (`branch_id`),
       KEY `idx_xid` (`xid`)
   ) ENGINE = InnoDB
     DEFAULT CHARSET = utf8mb4;
   
   CREATE TABLE IF NOT EXISTS `distributed_lock`
   (
       `lock_key`       CHAR(20) NOT NULL,
       `lock_value`     VARCHAR(20) NOT NULL,
       `expire`         BIGINT,
       primary key (`lock_key`)
   ) ENGINE = InnoDB
     DEFAULT CHARSET = utf8mb4;
   
   INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('AsyncCommitting', ' ', 0);
   INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryCommitting', ' ', 0);
   INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('RetryRollbacking', ' ', 0);
   INSERT INTO `distributed_lock` (lock_key, lock_value, expire) VALUES ('TxTimeoutCheck', ' ', 0);
   
   DROP TABLE IF EXISTS `lock_table`;
   CREATE TABLE `lock_table`  (
     `row_key` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '行键',
     `xid` varchar(96) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全局事务ID',
     `transaction_id` bigint(20) NULL DEFAULT NULL COMMENT '全局事务ID，不带TC 地址',
     `branch_id` bigint(20) NOT NULL COMMENT '分支ID',
     `resource_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源ID',
     `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
     `pk` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键对应的值',
     `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
     `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
     PRIMARY KEY (`row_key`) USING BTREE,
     INDEX `idx_branch_id`(`branch_id`) USING BTREE
   ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
   
   SET FOREIGN_KEY_CHECKS = 1;
   ```

6. 在Nacos创建Seata配置

   1. 创建命名空间：seata

   2. 创建配置

      DataID:SeataConfig.yaml

      Group:SEATA_GROUP

   3. 复制粘贴以下内容

      ```properties
      # 详细的参数配置请参考Seata网关：https://seata.io/zh-cn/docs/user/configurations.html
      
      # 传输配置，用于客户端和服务器
      transport:
        type: TCP # 传输类型，TCP
        server: NIO # 传输服务器类型，NIO
        heartbeat: true # 是否启用心跳
        enableTmClientBatchSendRequest: false # 是否启用TM客户端批量发送请求
        enableRmClientBatchSendRequest: true # 是否启用RM客户端批量发送请求
        enableTcServerBatchSendResponse: false # 是否启用TC服务器批量发送响应
        rpcRmRequestTimeout: 30000 # RM请求超时时间（毫秒）
        rpcTmRequestTimeout: 30000 # TM请求超时时间（毫秒）
        rpcTcRequestTimeout: 30000 # TC请求超时时间（毫秒）
        threadFactory:
          bossThreadPrefix: NettyBoss # boss线程名称前缀
          workerThreadPrefix: NettyServerNIOWorker # worker线程名称前缀
          serverExecutorThreadPrefix: NettyServerBizHandler # 服务器执行线程名称前缀
          shareBossWorker: false # 是否共享boss和worker线程池
          clientSelectorThreadPrefix: NettyClientSelector # 客户端选择器线程名称前缀
          clientSelectorThreadSize: 1 # 客户端选择器线程数
          clientWorkerThreadPrefix: NettyClientWorkerThread # 客户端工作线程名称前缀
          bossThreadSize: 1 # boss线程数
          workerThreadSize: default # worker线程数
        shutdown:
          wait: 3 # 关闭等待时间（秒）
        serialization: seata # 序列化方式，seata
        compressor: none # 压缩方式，无压缩
      
      # 事务路由规则配置，仅针对客户端
      service:
        vgroupMapping:
          tx_group: default # 事务分组映射
        default:
          grouplist: 127.0.0.1:8091 # 默认事务组的列表
        enableDegrade: false # 是否启用降级
        disableGlobalTransaction: false # 是否禁用全局事务
      
      # 事务规则配置，只针对客户端
      client:
        rm:
          asyncCommitBufferLimit: 10000 # 异步提交缓冲区限制
          lock:
            retryInterval: 10 # 锁重试间隔（毫秒）
            retryTimes: 30 # 锁重试次数
            retryPolicyBranchRollbackOnConflict: true # 在冲突时回滚分支事务
          reportRetryCount: 5 # 上报重试次数
          tableMetaCheckEnable: true # 是否启用表元数据检查
          tableMetaCheckerInterval: 60000 # 表元数据检查间隔（毫秒）
          sqlParserType: druid # SQL解析器类型，druid
          reportSuccessEnable: false # 是否启用上报成功
          sagaBranchRegisterEnable: false # 是否启用Saga分支注册
          sagaJsonParser: fastjson # Saga JSON解析器，fastjson
          tccActionInterceptorOrder: -2147482648 # TCC动作拦截器顺序
        tm:
          commitRetryCount: 5 # 提交重试次数
          rollbackRetryCount: 5 # 回滚重试次数
          defaultGlobalTransactionTimeout: 60000 # 默认全局事务超时时间（毫秒）
          degradeCheck: false # 是否启用降级检查
          degradeCheckAllowTimes: 10 # 降级检查允许次数
          degradeCheckPeriod: 2000 # 降级检查周期（毫秒）
          interceptorOrder: -2147482648 # 拦截器顺序
        undo:
          dataValidation: true # 是否启用数据校验
          logSerialization: jackson # 日志序列化方式，jackson
          onlyCareUpdateColumns: true # 是否只关注更新列
        store:
          mode: db # 存储模式，数据库模式
          lock:
            mode: db # 锁存储模式，数据库模式
          session:
            mode: db # 会话存储模式，数据库模式
          db:
            datasource: druid # 数据源类型，druid
            dbType: mysql # 数据库类型，mysql
            driverClassName: com.mysql.cj.jdbc.Driver # 数据库驱动类名
            url: jdbc:mysql://124.221.27.253:3306/seata?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false # 数据库连接URL
            user: root # 数据库用户名
            password: 2762581@com # 数据库密码
            minConn: 5 # 最小连接数
            maxConn: 30 # 最大连接数
            globalTable: global_table # 全局事务表名
            branchTable: branch_table # 分支事务表名
            distributedLockTable: distributed_lock # 分布式锁表名
            queryLimit: 100 # 查询限制
            lockTable: lock_table # 锁表名
            maxWait: 5000 # 最大等待时间（毫秒）
      
      # 事务规则配置，仅用于服务器
      server:
        recovery:
          committingRetryPeriod: 1000 # 提交事务重试周期（毫秒）
          asynCommittingRetryPeriod: 1000 # 异步提交事务重试周期（毫秒）
          rollbackingRetryPeriod: 1000 # 回滚事务重试周期（毫秒）
          timeoutRetryPeriod: 1000 # 超时事务重试周期（毫秒）
        maxCommitRetryTimeout: -1 # 最大提交重试超时时间（毫秒）
        maxRollbackRetryTimeout: -1 # 最大回滚重试超时时间（毫秒）
        rollbackRetryTimeoutUnlockEnable: false # 回滚重试超时解锁开关
        distributedLockExpireTime: 10000 # 分布式锁过期时间（毫秒）
        xaerNotaRetryTimeout: 60000 # XAER_NOTA重试超时时间（毫秒）
        session:
          branchAsyncQueueSize: 5000 # 分支异步队列大小
          enableBranchAsyncRemove: false # 是否启用分支异步删除
        enableParallelRequestHandle: false # 是否启用并行请求处理
      
      # 度量配置，仅适用于服务器
      metrics:
        enabled: false # 是否启用Metrics
        registryType: compact # Metrics注册表类型，compact
        exporterList: prometheus # Metrics导出器列表，prometheus
        exporterPrometheusPort: 9898 # Prometheus导出器端口
      ```

   4. 创建配置

      DataID:service.vgroupMapping.tx_group （tx_group为事务分组）

      Group:SEATA_GROUP

      内容：default

7. 重新启动容器

   ```shell
   docker stop seata
   
   docker run -d \
   -p 8091:8091 \
   -p 7091:7091 \
   -v /home/seata/config/resources:/seata-server/resources \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8091 \
   --name seata \
   --restart=always \
   seataio/seata-server:1.6.0
   ```
   
8. 集群启动

   ```shell
   docker run -d \
   -p 8091:8091 \
   -p 7091:7091 \
   -v /home/seata/config:/seata-server/resources \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8091 \
   -e SERVER_NODE=1 \
   --name seata1 \
   --restart=always \
   seataio/seata-server:1.6.0
   
   docker run -d \
   -p 8092:8092 \
   -p 7092:7091 \
   -v /home/seata/config:/seata-server/resources \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8092 \
   -e SERVER_NODE=2 \
   --name seata2 \
   --restart=always \
   seataio/seata-server:1.6.0
   
   
   docker run -d \
   -p 8093:8093 \
   -p 7093:7091 \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8093 \
   --name seata1 \
   --restart=always \
   dockerhub.kubekey.local/sipds/seata:1.6.0 
   ```
   
   

---



### Kafka

1. 拉取Kafka镜像 

   ```shell
   docker pull bitnami/kafka:latest
   ```

2. 启动容器

   ```shell
   docker run  -d -p 9092:9092 \
   -e ALLOW_PLAINTEXT_LISTENER=yes \
   -e KAFKA_TLS_CLIENT_AUTH=none \
   -e KAFKA_CFG_LISTENERS=PLAINTEXT://0.0.0.0:9092,CONTROLLER://localhost:9093 \
   -e KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://124.221.27.253:9092 \
   --name kafka  \
   --restart=always  \
   bitnami/kafka:latest
   ```




---



### Kafdrop

1. 拉取Kafka可视化工具

   ```shell
   docker pull obsidiandynamics/kafdrop:latest
   ```

2. 启动容器

   ```shell
   docker run -d -p 9000:9000 \
   -e KAFKA_BROKERCONNECT=124.221.27.253:9092 \
   -e JVM_OPTS="-Xms128M -Xmx128M" \
   -e SERVER_SERVLET_CONTEXTPATH="/" \
   --name kafkaView  \
   --restart=always  \
   obsidiandynamics/kafdrop:latest
   ```
   
3. 访问Web界面

   ```shell
   http://124.221.27.253:9000/
   ```

   

---



### ElasticSearch

1. 拉取ES镜像

   ```shell
   docker pull docker.elastic.co/elasticsearch/elasticsearch:8.8.2
   ```

2. 创建网络

   ```shell
   docker network create es-net
   ```

3. 运行ES容器

   ```shell
   docker run -d -it \
   --name es \
   -p 9200:9200 \
   -p 9300:9300 \
   --privileged \
   --net es-net \
   -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
   -e "discovery.type=single-node" \
   -e ELASTIC_PASSWORD=elastic \
   -e KIBANA_PASSWORD=kibana \
   --restart=always \
   docker.elastic.co/elasticsearch/elasticsearch:8.8.2
   ```

4. 查看Log，但是Kibana不需要Token，而是手动连接ES

   ```shell
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   ✅ Elasticsearch security features have been automatically configured!
   ✅ Authentication is enabled and cluster connections are encrypted.
   
   
   
   ℹ️  HTTP CA certificate SHA-256 fingerprint:
     2eece7079b42d56e164a3031e88042f41e996e266024b483e12d4f7b9bb89613
   
   ℹ️  Configure Kibana to use this cluster:
   • Run Kibana and click the configuration link in the terminal when Kibana starts.
   • Copy the following enrollment token and paste it into Kibana in your browser (valid for the next 30 minutes):
     eyJ2ZXIiOiI4LjguMiIsImFkciI6WyIxNzIuMTguMC4yOjkyMDAiXSwiZmdyIjoiMmVlY2U3MDc5YjQyZDU2ZTE2NGEzMDMxZTg4MDQyZjQxZTk5NmUyNjYwMjRiNDgzZTEyZDRmN2I5YmI4OTYxMyIsImtleSI6ImJDU0hVNGtCYWlzdU9XSkdqNmcyOkZvQUFpdUlJUlk2UTZLdDhpckJxX0EifQ==
   
   ℹ️ Configure other nodes to join this cluster:
   • Copy the following enrollment token and start new Elasticsearch nodes with `bin/elasticsearch --enrollment-token <token>` (valid for the next 30 minutes):
     eyJ2ZXIiOiI4LjguMiIsImFkciI6WyIxNzIuMTguMC4yOjkyMDAiXSwiZmdyIjoiMmVlY2U3MDc5YjQyZDU2ZTE2NGEzMDMxZTg4MDQyZjQxZTk5NmUyNjYwMjRiNDgzZTEyZDRmN2I5YmI4OTYxMyIsImtleSI6ImFpU0hVNGtCYWlzdU9XSkdqNmdkOk50dzVJQ1NOVEt1Y3FyN3ZCdF9uOWcifQ==
   
     If you're running in Docker, copy the enrollment token and run:
     `docker run -e "ENROLLMENT_TOKEN=<token>" docker.elastic.co/elasticsearch/elasticsearch:8.8.2`
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   
   ```

5. 将容器中配置文件Copy出来

   ```shell
   sudo docker cp es:/usr/share/elasticsearch/config/elasticsearch.yml /home/es/config
   ```

6. 修改配置文件为以下内容

   ```shell
   cluster.name: "docker-cluster"
   network.host: 0.0.0.0
   
   # 启用安全功能
   xpack.security.enabled: true
   xpack.security.enrollment.enabled: true
   
   # 启用加密 HTTP API客户端连接(如Kibana、Logstash和Agents)
   xpack.security.http.ssl:
     enabled: false
     keystore.path: certs/http.p12
   
   # 启用集群节点之间的加密和相互认证
   xpack.security.transport.ssl:
     enabled: true
     verification_mode: certificate
     keystore.path: certs/transport.p12
     truststore.path: certs/transport.p12
   ```
   
7. 将配置文件Copy回容器中，重启容器

   ```shell
   sudo docker cp /home/es/config/elasticsearch.yml  es:/usr/share/elasticsearch/config/
   
   docker restart es
   ```

8. 进入容器，重置默认密码

   ```shell
   docker exec -it es /bin/bash
   
   cd /bin
   
   elasticsearch-reset-password --username elastic -i
   
   -- 建议密码修改为:elastic
   
   elasticsearch-reset-password --username kibana_system -i
   
   -- 建议密码修改为:kibana
   
   exit
   ```
   
   

---



### Kibana

1. 拉取Kibana镜像

   ```shell
   docker pull docker.elastic.co/kibana/kibana:8.8.2
   ```

2. 启动Kibana

   ```shell
   docker run -d \
   --name kibana \
   -p 5601:5601 \
   --net es-net \
   -e "I18N_LOCALE=zh-CN" \
   --restart=always \
   docker.elastic.co/kibana/kibana:8.8.2
   ```

3. 访问ES，输出一下内容，表示ES连接成功

   ```json
   {
     "name" : "a6cecc1a6c80",
     "cluster_name" : "docker-cluster",
     "cluster_uuid" : "Bvb5R4KpQFW2h2ouYFuxOA",
     "version" : {
       "number" : "8.8.2",
       "build_flavor" : "default",
       "build_type" : "docker",
       "build_hash" : "98e1271edf932a480e4262a471281f1ee295ce6b",
       "build_date" : "2023-06-26T05:16:16.196344851Z",
       "build_snapshot" : false,
       "lucene_version" : "9.6.0",
       "minimum_wire_compatibility_version" : "7.17.0",
       "minimum_index_compatibility_version" : "7.0.0"
     },
     "tagline" : "You Know, for Search"
   }
   ```

   

4. 访问kibana网址:

   http://IP:5601

5. 手动连接ES

6. 重置Kibana密码

   ```shell
   docker exec -it es /bin/bash
   
   cd /bin
   
   elasticsearch-reset-password --username elastic -i
   ```

7. 输入kibana_system账号的密码

8. 输入ES账号密码

9. 可以通过修改kibana.yml配置文件修改用户名密码

   ```shell
   sudo docker cp kibana:/usr/share/kibana/config/kibana.yml /home/kibana/config
   
   sudo docker cp /home/kibana/config/kibana.yml  kibana:/usr/share/kibana/config/
   
   docker restart kibana
   ```

10. 安装IK分词器

    ```shell
    docker exec -it es容器ID /bin/bash
    
    ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.8.2/elasticsearch-analysis-ik-8.8.2.zip
    
    docker restart es
    ```



---



### MySQL

1. 拉取MySQL镜像

   ```shell
   docker pull mysql:8.0.32
   ```

2. 启动MySQL

   ```shell
   docker run -itd \
   --name mysql \
   -p 3306:3306 \
   -e MYSQL_ROOT_PASSWORD=2762581@com \
   --restart=always \
   mysql:8.0.32
   ```




---



### Sentinel

1. 拉取Sentinel镜像

   ```shell
   docker pull bladex/sentinel-dashboard:latest
   ```

2. 启动Sentinel

   ```shell
   docker run -d \
   --name sentinel \
   -p 8858:8858 \
   --restart=always \
   bladex/sentinel-dashboard:latest
   
   sudo docker cp /home/es/config/elasticsearch.yml  es:/usr/share/elasticsearch/config/
   ```



---



### RabbitMQ

1. 拉取RabbitMQ镜像

   ```shell
   docker pull rabbitmq:latest
   ```

2. 启动RabbitMQ

   ```shell
   docker run -d \
   -p 15672:15672  \
   -p 5672:5672   \
   -e RABBITMQ_DEFAULT_USER=root  \
   -e RABBITMQ_DEFAULT_PASS=2762581@com  \
   -e RABBITMQ_MANAGEMENT=true \
   --name rabbitmq \
   --hostname rabbitmq \  
   --restart=always \
   rabbitmq:latest
   ```

3. 安装依赖

   ```sh
   docker exec -it rabbitmq /bin/bash # 进入RabbitMQ容器
   
   rabbitmq-plugins enable rabbitmq_management # 下载enable语言依赖
   
   cd /etc/rabbitmq/conf.d
   
   echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf
   
   exit # 退出容器
   
   docker restart rabbitmq # 重启RabbitMQ容器
   ```
   
   

---



### ActiveMq



1.拉取ActiveMq镜像

```shell
docker pull islandora/activemq:main
```
2.启动ActiveMq

```shell
docker run -d \
-p 8161:8161 \
-p 61616:61616 \
-e ACTIVEMQ_USER=root \
-e ACTIVEMQ_PASSWORD=2762581@com \
--name activemq \
--restart=always \
islandora/activemq:main
```



---

