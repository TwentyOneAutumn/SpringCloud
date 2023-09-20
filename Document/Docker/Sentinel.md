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

