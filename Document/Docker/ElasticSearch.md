# Docker deploy ElasticSearch

### ElasticSearch

1. 拉取ElasticSearch镜像

   ```shell
   docker pull elasticsearch:8.1.2
   ```

2. 创建网络

   ```shell
   docker network create es-net
   ```

3. 创建 elasticsearch.yml

   ```yaml
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

4. 运行ES容器

   ```shell
   docker run -it \
   -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
   -e "discovery.type=single-node" 
   --privileged \
   --net es-net 
   -p 9200:9200 \
   -p 9300:9300 \
   -v /home/es/config/:/usr/share/elasticsearch/config/
   --name es \
   --restart=always \
   elasticsearch:8.1.2
   ```

5. 查看Log

6. 将容器中配置文件Copy出来

7. 修改配置文件为以下内容

8. 
