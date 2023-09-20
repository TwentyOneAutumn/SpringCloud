# Docker deploy Kibana

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

