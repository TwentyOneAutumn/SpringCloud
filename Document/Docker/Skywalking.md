# Docker deploy Skywalking

### Skywalking

1. 创建网络

   ```shell
   docker network create skywalking-net
   ```

2. 拉取 Skywalking OAP Server 镜像

   ```shell
   docker pull apache/skywalking-oap-server:9.7.0
   ```

3. 部署 Skywalking OAP Server

   ```shell
   docker run -d \
   -p 12800:12800 \
   -p 11800:11800 \
   -p 1234:1234 \
   -e SW_STORAGE=elasticsearch \
   -e SW_STORAGE_ES_CLUSTER_NODES=10.211.55.12:9200 \
   -e SW_ES_PASSWORD=elastic \
   -e SW_ES_USER=elastic \
   -e SW_TELEMETRY=prometheus \
   -e SW_TELEMETRY_PROMETHEUS_HOST=10.211.55.12 \
   -e TZ=Asia/Shanghai \
   --net skywalking-net \
   --privileged \
   --name oap \
   apache/skywalking-oap-server:9.7.0
   ```
   
4. 拉取 Skywalking UI 镜像

   ```shell
   docker pull apache/skywalking-ui:9.7.0
   ```

5. 部署 Skywalking UI

   ```shell
   docker run -d \
   -p 9080:8080 \
   --link oap:oap \
   -e SW_OAP_ADDRESS=http://oap:12800 \
   -e TZ=Asia/Shanghai \
   --net skywalking-net \
   --name ui \
   apache/skywalking-ui:9.7.0
   ```

6. 访问 http://10.211.55.12:9080



