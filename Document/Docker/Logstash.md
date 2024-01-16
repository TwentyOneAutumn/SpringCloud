# Docker deploy Logstash

### Logstash

1. 拉取Logstash镜像

   ```shell
   docker pull logstash:8.11.3
   ```

2. 启动容器

   ```shell
   docker run -d -it \
   -p 5044:5044 \
   --name logstash \
   logstash:8.11.3
   ```

3. 从容器中copy文件夹

   ```shell
   docker cp logstash:/usr/share/logstash/config ./
   docker cp logstash:/usr/share/logstash/pipeline ./
   ```

4. 修改两个配置文件夹中的配置

   /usr/share/logstash/config/logstash.yml

   ```shell
   http.host: "0.0.0.0"
   xpack.monitoring.elasticsearch.hosts: [ "http://10.211.55.55:9200" ]
   ```

   /usr/share/logstash/pipeline/logstash.conf

   ```shell
   input {
       tcp {
           mode => "server"
           host => "0.0.0.0"
           port => 6399
           codec => jsonlines
       }
   }
   output {
       elasticsearch {
           hosts => ["http://10.211.55.55:9200"]
           index => "basic-%{+YYYY.MM.dd}"
           document_type => "_doc"
           user => "elastic"
           password => "elastic"
       }
   }
   ```

5. 删除旧容器

   ```shell
   docker stop logstash && docker rm logstash
   ```

6. 启动新容器

   ```shell
   docker run -d -it \
   -p 5044:5044 \
   -p 6399:6399 \
   -v /root/logstash/config:/usr/share/logstash/config \
   -v /root/logstash/pipeline:/usr/share/logstash/pipeline \
   --name logstash \
   logstash:8.11.3
   ```