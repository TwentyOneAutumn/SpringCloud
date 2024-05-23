# Docker deploy ElasticSearch

### ElasticSearch

1. 拉取ElasticSearch镜像

   ```shell
   docker pull elasticsearch:8.13.3
   ```

2. 创建网络

   ```shell
   docker network create es-net
   ```

3. 运行ES容器

   ```shell
   docker run -d -it \
   -e "ES_JAVA_OPTS=-Xms1g -Xmx1g" \
   -e "discovery.type=single-node" \
   -e "ELASTIC_PASSWORD=elastic" \
   -e "KIBANA_PASSWORD=kibana" \
   --privileged \
   --net es-net \
   -p 9200:9200 \
   -p 9300:9300 \
   --name es \
   elasticsearch:8.13.3
   ```

4. 复制容器中配置

   ```shell
   docker cp es:/usr/share/elasticsearch/config ./
   ```

5. 修改配置文件elasticsearch.yml

   ```shell
   xpack.security.enabled: false
   ```

6. 删除旧容器

   ```shell
   docker stop es && docker rm es
   ```

7. 重新启动容器

   ```shell
   docker run -d -it \
   -e "ES_JAVA_OPTS=-Xms1g -Xmx1g" \
   -e "discovery.type=single-node" \
   -e "ELASTIC_PASSWORD=elastic" \
   -e "KIBANA_PASSWORD=kibana" \
   --privileged \
   --net es-net \
   -p 9200:9200 \
   -p 9300:9300 \
   -v /root/es/config:/usr/share/elasticsearch/config \
   --name es \
   elasticsearch:8.13.3
   ```

8. 访问 http://ip:9200

   ```json
   {
     "name" : "9393485e5d28",
     "cluster_name" : "docker-cluster",
     "cluster_uuid" : "Fv9oWWCHRBWrjArBHWfrVA",
     "version" : {
       "number" : "8.11.3",
       "build_flavor" : "default",
       "build_type" : "docker",
       "build_hash" : "64cf052f3b56b1fd4449f5454cb88aca7e739d9a",
       "build_date" : "2023-12-08T11:33:53.634979452Z",
       "build_snapshot" : false,
       "lucene_version" : "9.8.0",
       "minimum_wire_compatibility_version" : "7.17.0",
       "minimum_index_compatibility_version" : "7.0.0"
     },
     "tagline" : "You Know, for Search"
   }
   ```
