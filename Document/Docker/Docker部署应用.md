# Docker部署应用



---



### Nacos

1. 拉取Nacos镜像

   ```shell
   docker pull nacos/nacos-server
   ```

2. 启动容器

   ```shell
   docker run --env MODE=standalone --restart=always --name nacos_server -d -p 8848:8848 nacos/nacos-server
   ```
   
   

---



### Docker安装Redis

1. 拉取Redis镜像 

   ```shell
   docker pull redis:latest
   ```

1. 启动容器

   ```shell
   docker run --restart=always --log-opt max-size=100m --log-opt max-file=2 -p 6379:6379 --name redis -v /home/redis/redis.conf:/etc/redis/redis.conf -v /home/redis/data:/data -d redis redis-server /etc/redis/redis.conf  --appendonly yes  --requirepass redis
   ```

1. 修改配置文件redis.conf



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
   cd /home/seata/config
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
      #For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
      #Transport configuration, for client and server
      transport.type=TCP
      transport.server=NIO
      transport.heartbeat=true
      transport.enableTmClientBatchSendRequest=false
      transport.enableRmClientBatchSendRequest=true
      transport.enableTcServerBatchSendResponse=false
      transport.rpcRmRequestTimeout=30000
      transport.rpcTmRequestTimeout=30000
      transport.rpcTcRequestTimeout=30000
      transport.threadFactory.bossThreadPrefix=NettyBoss
      transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
      transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
      transport.threadFactory.shareBossWorker=false
      transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
      transport.threadFactory.clientSelectorThreadSize=1
      transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
      transport.threadFactory.bossThreadSize=1
      transport.threadFactory.workerThreadSize=default
      transport.shutdown.wait=3
      transport.serialization=seata
      transport.compressor=none
      
      #Transaction routing rules configuration, only for the client
      # 此处的mygroup名字可以自定义，只修改这个值即可
      service.vgroupMapping.mygroup=default
      #If you use a registry, you can ignore it
      service.default.grouplist=127.0.0.1:8091
      service.enableDegrade=false
      service.disableGlobalTransaction=false
      
      #Transaction rule configuration, only for the client
      client.rm.asyncCommitBufferLimit=10000
      client.rm.lock.retryInterval=10
      client.rm.lock.retryTimes=30
      client.rm.lock.retryPolicyBranchRollbackOnConflict=true
      client.rm.reportRetryCount=5
      client.rm.tableMetaCheckEnable=true
      client.rm.tableMetaCheckerInterval=60000
      client.rm.sqlParserType=druid
      client.rm.reportSuccessEnable=false
      client.rm.sagaBranchRegisterEnable=false
      client.rm.sagaJsonParser=fastjson
      client.rm.tccActionInterceptorOrder=-2147482648
      client.tm.commitRetryCount=5
      client.tm.rollbackRetryCount=5
      client.tm.defaultGlobalTransactionTimeout=60000
      client.tm.degradeCheck=false
      client.tm.degradeCheckAllowTimes=10
      client.tm.degradeCheckPeriod=2000
      client.tm.interceptorOrder=-2147482648
      client.undo.dataValidation=true
      client.undo.logSerialization=jackson
      client.undo.onlyCareUpdateColumns=true
      server.undo.logSaveDays=7
      server.undo.logDeletePeriod=86400000
      client.undo.logTable=undo_log
      client.undo.compress.enable=true
      client.undo.compress.type=zip
      client.undo.compress.threshold=64k
      #For TCC transaction mode
      tcc.fence.logTableName=tcc_fence_log
      tcc.fence.cleanPeriod=1h
      
      #Log rule configuration, for client and server
      log.exceptionRate=100
      
      #Transaction storage configuration, only for the server. The file, db, and redis configuration values are optional.
      # 默认为file，一定要改为db，我们自己的服务启动会连接不到seata
      store.mode=db
      store.lock.mode=db
      store.session.mode=db
      #Used for password encryption
      
      #These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
      # 修改mysql的配置
      store.db.datasource=druid
      store.db.dbType=mysql
      store.db.driverClassName=com.mysql.cj.jdbc.Driver
      # 指定seata的数据库，下面会提
      store.db.url=jdbc:mysql://124.221.27.253:3306/seata?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
      store.db.user=root
      store.db.password=2762581@com
      store.db.minConn=5
      store.db.maxConn=30
      store.db.globalTable=global_table
      store.db.branchTable=branch_table
      store.db.distributedLockTable=distributed_lock
      store.db.queryLimit=100
      store.db.lockTable=lock_table
      store.db.maxWait=5000
      
      
      #Transaction rule configuration, only for the server
      server.recovery.committingRetryPeriod=1000
      server.recovery.asynCommittingRetryPeriod=1000
      server.recovery.rollbackingRetryPeriod=1000
      server.recovery.timeoutRetryPeriod=1000
      server.maxCommitRetryTimeout=-1
      server.maxRollbackRetryTimeout=-1
      server.rollbackRetryTimeoutUnlockEnable=false
      server.distributedLockExpireTime=10000
      server.xaerNotaRetryTimeout=60000
      server.session.branchAsyncQueueSize=5000
      server.session.enableBranchAsyncRemove=false
      server.enableParallelRequestHandle=false
      
      #Metrics configuration, only for the server
      metrics.enabled=false
      metrics.registryType=compact
      metrics.exporterList=prometheus
      metrics.exporterPrometheusPort=9898
      ```

7. 重新启动容器

   ```shell
   docker stop seata
   
   docker run -d \
   -p 8091:8091 \
   -p 7091:7091 \
   -v /home/seata/config:/seata-server/resources \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8091 \
   --name seata \
   --restart=always \
   seataio/seata-server:1.6.0
   ```
   
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
   -v /home/seata/config:/seata-server/resources \
   -e SEATA_IP=124.221.27.253 \
   -e SEATA_PORT=8093 \
   -e SERVER_NODE=3 \
   --name seata3 \
   --restart=always \
   seataio/seata-server:1.6.0
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

6. 输入kibana_system账号的密码

7. 输入ES账号密码

8. xxx

   ```shell
   sudo docker cp kibana:/usr/share/kibana/config/kibana.yml /home/kibana/config
   
   
   
   sudo docker cp /home/kibana/config/kibana.yml  kibana:/usr/share/kibana/config/
   ```

   

   

---



### Docker部署MySQL

1. 拉取MySQL镜像

   ```shell
   docker pull mysql:8.0.32
   ```

2. 启动MySQL

   ```shell
   docker run -itd --name mysql -p 3306:3306 --restart=always -e MYSQL_ROOT_PASSWORD=2762581@com mysql:8.0.32
   ```




---



### Sentinel

1. 拉取Sentinel镜像

   ```shell
   docker pull bladex/sentinel-dashboard:latest
   ```

2. 启动Sentinel

   ```shell
   docker run -d --name sentinel -p 8858:8858 --restart=always bladex/sentinel-dashboard:latest
   
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
   docker run -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=2762581@com -e RABBITMQ_MANAGEMENT=true --name rabbitmq --hostname rabbitmq  -p 15672:15672 -p 5672:5672 -d rabbitmq:latest
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

