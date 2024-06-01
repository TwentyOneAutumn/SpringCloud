# Docker deploy Logstash

### Logstash

1. 拉取Logstash镜像

   ```shell
   docker pull logstash:8.13.3
   ```

2. 启动容器

   ```shell
   docker run --rm -d -it \
   -p 5044:5044 \
   --name logstash \
   logstash:8.13.3
   ```

3. 从容器中copy文件夹

   ```shell
   docker cp logstash:/usr/share/logstash/config ./config
   docker cp logstash:/usr/share/logstash/pipeline ./pipeline
   ```

4. 修改两个配置文件夹中的配置

   ```shell
   vim ./config/logstash.yml
   
   # 写入配置
   cat > 文件名 <<EOF
   http.host: "0.0.0.0"
   xpack.monitoring.elasticsearch.hosts: [ "http://10.211.55.55:9200" ]
   EOF
   ```

   编写规则

   ```shell
   vim ./pipeline/logstash.conf
   
   # logstash.conf
   input {
       tcp {
           mode => "server"
           host => "0.0.0.0"
           port => 6399
           codec => "json"
       }
   }
   
   filter {
       grok {
           match => { "message" => '\{"service":"[^"]+","module":"[^"]+","className":"[^"]+","level":"[^"]+","message":"[^"]+"\}' }
           tag_on_failure => ["grok_regex_match_failure"]
       }
   
       if "grok_regex_match_failure" in [tags] {
           drop {}
       }
   }
   ```

5. 删除旧容器

   ```shell
   docker stop logstash
   ```

6. 启动新容器

   ```shell
   docker run -d -it \
   -p 5044:5044 \
   -p 6399:6399 \
   -v ./config:/usr/share/logstash/config \
   -v ./pipeline:/usr/share/logstash/pipeline \
   --name logstash \
   logstash:8.13.3
   ```