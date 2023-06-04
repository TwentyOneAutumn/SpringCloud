# Docker部署应用



---



### Docker安装Nacos

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



### Docker安装Seata

1. 拉取Seata镜像 

   ```shell
   docker pull seataio/seata-server:1.6.0-SNAPSHOT
   ```

2. 创建目录

   ```shell
   mkdir -p /home/seata/config/resources
   ```

3. 在目录下创建文件application.yml并编辑

   ```yaml
   server: 
     port: 7091
   spring:
     application:
       name: seata    
   console:
     user:
       username: seata
       password: seata
   seata:
     config:
       type: nacos
       nacos:
         server-addr: 192.168.111.111:8848
         group: SEATA_GROUP
         namespace: 25da6d30-663a-49ca-abde-4f1a805a305b
         username: nacos
         password: nacos
         data-id: seataServer.properties  
     registry:
       type: nacos
       nacos:
         server-addr: 192.168.111.111:8848
         group: SEATA_GROUP
         namespace: 25da6d30-663a-49ca-abde-4f1a805a305b
         username: nacos
         password: nacos
     store:
       mode: db
       db:
         datasource: druid
         dbType: mysql
         driverClassName: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://192.168.111.111:3306/seata?useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
         user: root
         password: Root@2022
         minConn: 5
         maxConn: 100
         globalTable: global_table
         branchTable: branch_table
         lockTable: lock_table
         queryLimit: 100
         maxWait: 5000
     security:
       secretKey: root
       tokenValidityInMilliseconds: 604800
   security:
       secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
       tokenValidityInMilliseconds: 1800000
       ignore:
         urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
   ```

4. 创建seata依赖表

   ```sql
   -- 创建seata数据库
   -- 创建seata依赖表
   CREATE TABLE IF NOT EXISTS `undo_log`
   (
       `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
       `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
       `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
       `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
       `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
       `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
       `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
       UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
   ) ENGINE = InnoDB
     AUTO_INCREMENT = 1
     DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';
   ```

5. 启动容器

   ```shell
   docker run --restart=always -d -p 8091:8091 -p 7091:7091 --name seata -e SEATA_IP=192.168.111.111 -v /home/seata/config/resources:/seata-server/resources  seataio/seata-server:1.6.0-SNAPSHOT
   ```
   
   

---



### Docker安装Zookeeper

1. 拉取Zookeeper镜像 

   ```shell
   docker pull wurstmeister/zookeeper:latest
   ```

2. 启动容器

   ```shell
   docker run -d --restart=always --log-driver json-file --log-opt max-size=100m --log-opt max-file=2  --name zookeeper -p 2181:2181 -v /etc/localtime:/etc/localtime wurstmeister/zookeeper
   ```
   
   

---



### Docker安装Kafka

1. Kafka依赖Zookeeper，需要先安装Zookeeper

2. 拉取Kafka镜像 

   ```shell
   docker pull wurstmeister/kafka:latest
   ```

3. 启动容器

   ```shell
   docker run -d  --restart=always --log-driver json-file --log-opt max-size=100m --log-opt max-file=2 --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.111.111:2181/kafka -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.111.111:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka
   ```
   
   

---



### Docker安装ES

1. 拉取ES镜像

   ```shell
   docker pull elasticsearch:8.1.2
   ```

2. 创建网络

   ```shell
   docker network create es-net
   ```

3. 运行ES容器

   ```shell
   docker run --name es -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" -e "discovery.type=single-node"  --privileged --net es-net -p 9200:9200 -p 9300:9300 -it  --restart=always elasticsearch:8.1.2
   ```

4. 查看Log

   ```shell
   -------------------------------------------------------------------------------------------------------------------------------------------------------
   -> Elasticsearch security features have been automatically configured!
   -> Authentication is enabled and cluster connections are encrypted.
   
   ->  Password for the elastic user (reset with `bin/elasticsearch-reset-password -u elastic`):
   ->  初始密码
     HpWHQ7UXq-VRL7Sre0dD
   
   ->  HTTP CA certificate SHA-256 fingerprint:
     4f23ff962e5c639af32565ffd2cfbc244fc83fe577796fd54bc264b3dd2155ee
   
   ->  Configure Kibana to use this cluster:
   * Run Kibana and click the configuration link in the terminal when Kibana starts.
   * Copy the following enrollment token and paste it into Kibana in your browser (valid for the next 30 minutes):
    
   ->  初始token,用来连接Kibnana eyJ2ZXIiOiI4LjEuMiIsImFkciI6WyIxNzIuMTguMC4yOjkyMDAiXSwiZmdyIjoiNGYyM2ZmOTYyZTVjNjM5YWYzMjU2NWZmZDJjZmJjMjQ0ZmM4M2ZlNTc3Nzk2ZmQ1NGJjMjY0YjNkZDIxNTVlZSIsImtleSI6InZCYzFDb1VCbjE4SFRIa2xUNE5qOnR2Z0c5OFlSUllHSVUxNGhSdWwyUFEifQ==
   
   -> Configure other nodes to join this cluster:
   * Copy the following enrollment token and start new Elasticsearch nodes with `bin/elasticsearch --enrollment-token <token>` (valid for the next 30 minutes):
     eyJ2ZXIiOiI4LjEuMiIsImFkciI6WyIxNzIuMTguMC4yOjkyMDAiXSwiZmdyIjoiNGYyM2ZmOTYyZTVjNjM5YWYzMjU2NWZmZDJjZmJjMjQ0ZmM4M2ZlNTc3Nzk2ZmQ1NGJjMjY0YjNkZDIxNTVlZSIsImtleSI6InZoYzFDb1VCbjE4SFRIa2xUNE5rOldNQklZVXVrVHFpejVPV3NOWVdpdlEifQ==
   
     If you're running in Docker, copy the enrollment token and run:
     `docker run -e "ENROLLMENT_TOKEN=<token>" docker.elastic.co/elasticsearch/elasticsearch:8.1.2`
   -------------------------------------------------------------------------------------------------------------------------------------------------------
   ```

5. 将容器中配置文件Copy出来

   ```shell
   sudo docker cp es:/usr/share/elasticsearch/config/elasticsearch.yml /home/es/config
   ```

6. 修改配置文件为以下内容

   ```shell
   cluster.name: "docker-cluster"
   network.host: 0.0.0.0
   
   #----------------------- BEGIN SECURITY AUTO CONFIGURATION -----------------------
   #
   # The following settings, TLS certificates, and keys have been automatically      
   # generated to configure Elasticsearch security features on 13-12-2022 00:39:46
   #
   # --------------------------------------------------------------------------------
   
   # Enable security features
   xpack.security.enabled: true
   xpack.security.audit.enabled: true
   xpack.license.self_generated.type: basic
   xpack.security.enrollment.enabled: true
   
   # Enable encryption for HTTP API client connections, such as Kibana, Logstash, and Agents
   xpack.security.http.ssl:
     enabled: false
     keystore.path: certs/http.p12
   
   # Enable encryption and mutual authentication between cluster nodes
   xpack.security.transport.ssl:
     enabled: true
     verification_mode: certificate
     keystore.path: certs/transport.p12
     truststore.path: certs/transport.p12
   #----------------------- END SECURITY AUTO CONFIGURATION -------------------------
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
   
   -- 建议密码修改为:kibana_system
   
   exit
   ```
   
9. 手动生成Token

   ```shell
   docker exec -it es /bin/bash
   
   bin/elasticsearch-create-enrollment-token -s kibana --url "https://localhost:9200"
   ```

   

---



### Docker部署Kibana

1. 拉取Kibana镜像

   ```shell
   docker pull kibana:8.1.2
   ```

2. 启动Kibana

   ```shell
   docker run --name kib --net es-net -p 5601:5601 --restart=always kibana:8.1.2
   ```

3. 访问kibana网址:

   http://192.168.111.111:5601

4. 手动连接ES

   http://192.168.111.111:9200

5. 查看验证码

   ```shell
   docker logs kib
   ```

6. 输入账号密码

   ```tex
   kibana_system
   ```



---



### Docker部署MySQL

1. 拉取MySQL镜像

   ```shell
   docker pull mysql:8.0.32
   ```

2. 启动MySQL

   ```shell
   docker run -itd --name mysql -p 3306:3306 --restart=always -e MYSQL_ROOT_PASSWORD=root mysql:8.0.32
   ```




---



### Docker部署Sentinel

1. 拉取Sentinel镜像

   ```shell
   docker pull bladex/sentinel-dashboard:latest
   ```

2. 启动Sentinel

   ```shell
   docker run -d --name sentinel -p 8858:8858 --restart=always bladex/sentinel-dashboard:latest
   ```



