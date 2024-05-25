# Docker deploy Kafka

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

