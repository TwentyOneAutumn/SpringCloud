# Docker deploy Kibana

### Kibana

1. 拉取Kibana镜像

   ```shell
   docker pull docker.elastic.co/kibana/kibana:8.11.3
   ```

2. 启动Kibana

   ```shell
   docker run -d \
   --name kibana \
   -p 5601:5601 \
   --net es-net \
   -e "I18N_LOCALE=zh-CN" \
   kibana:8.11.3
   ```

3. 访问kibana http://ip:5601

   ![kibana1](..\img\es\kibana1.png)

   ![kibana2](..\img\es\kibana2.png)

   ![kibana3](..\img\es\kibana3.png)

   ![kibana4](..\img\es\kibana4.png)

   查看日志

   ```
   docker logs kibana -f
   ```

   查看验证码

   ```shell
   Your verification code is:  XXX XXX
   ```

   ![kibana5](..\img\es\kibana5.png)

   ![kibana6](..\img\es\kibana6.png)

4. 安装IK分词器

   ```shell
   docker exec -it es容器ID /bin/bash
   
   ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.11.3/elasticsearch-analysis-ik-8.11.3.zip
   
   docker restart es
   ```
