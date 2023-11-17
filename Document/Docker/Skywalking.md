# Docker deploy Skywalking

### Skywalking

1. 拉取 Skywalking OAP Server 镜像

   ```shell
   docker pull apache/skywalking-oap-server:9.6.0
   ```

2. 部署 Skywalking OAP Server

   ```shell
   docker run -d \
   -p 12800:12800 \
   -p 11800:11800 \
   -e SW_STORAGE=elasticsearch \
   -e SW_STORAGE_ES_CLUSTER_NODES=124.221.27.253:9200 \
   -e SW_ES_PASSWORD=elastic \
   -e SW_ES_USER=elastic \
   -e TZ=Asia/Shanghai \
   --name oap \
   --restart always \
   apache/skywalking-oap-server:9.6.0
   ```

3. 拉取 Skywalking UI 镜像

   ```shell
   docker pull apache/skywalking-ui:9.6.0
   ```

4. 部署 Skywalking UI

   ```shell
   docker run -d \
   -p 9080:8080 \
   --link oap:oap \
   -e SW_OAP_ADDRESS=http://oap:12800 \
   -e TZ=Asia/Shanghai \
   --name ui \
   --restart always \
   apache/skywalking-ui:9.6.0
   ```

5. 

