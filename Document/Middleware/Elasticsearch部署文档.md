# Elasticsearch安装文档

##### ES账号密码

```
elastic
elastic
```



##### Docker拉取Elasticsearch镜像

```sh
# 拉取Elasticsearch镜像
docker pull elasticsearch:8.1.2



# 拉取kibana镜像
docker pull kibana:8.1.2



# 创建网络
docker network create es-net



# 运行Elasticsearch
docker run --name es --restart=always  --net es-net -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" -e "discovery.type=single-node"  -p 9200:9200 -p 9300:9300 -it elasticsearch:8.1.2
 
 docker run --name es -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" -e "discovery.type=single-node" --net es-net -p 9200:9200 -p 9300:9300 -it --restart=always elasticsearch:8.1.2
 
  docker run --name es --net es-net -e "discovery.type=single-node" -p 9200:9200 -p 9300:9300 -it elasticsearch:8.1.2
 
 
 
 
 # 运行后日志会有初始的User和password
 # 会有kibana连接的token
 
 
 # 如果token过期,可以手动生成token
 # 进入es容器
 # 运行生成token命令
docker exec -it es /bin/bash
bin/elasticsearch-create-enrollment -token -s kibana --url "http://localhost:9200"



# 可以重置es用户名和密码
# 默认用户名为elastic,通过一下命令重置密码
docker exec -it es /bin/bash
elasticsearch-reset-password --username elastic -i
 
 

# 运行kibana
 docker run --name kib --net es-net -p 5601:5601 -e ELASTICSEARCH_HOSTS=http://192.168.111.111:9200 --restart=always kibana:8.1.2
 



# 访问kibana网址:[http://192.168.111.111:5601/?code=xxxxxx],输入token连接es,输入账号密码来连接es
```



##### 修改Elasticsearch.yml配置文件

```yaml
# 将容器中的配置文件copy到本地
sudo docker cp es:/usr/share/elasticsearch/config/elasticsearch.yml /home/es/config
 
# 在本地修改enabled为false
xpack.security.http.ssl:
  enabled: false
  
# 将本地修改后的配置文件copy到容器中
sudo docker cp /home/es/config/elasticsearch.yml  es:/usr/share/elasticsearch/config/


# 将容器中的配置文件copy到本地
sudo docker cp kib:/usr/share/kibana/config/kibana.yml /home/kib/config
 
# 在本地修改enabled为false
xpack.security.http.ssl:
  enabled: false
  
# 将本地修改后的配置文件copy到容器中
sudo docker cp /home/kib/config  kib:/usr/share/kibana/config/

```



##### Elasticsearch.yml配置文件内容

```yaml
cluster.name: "docker-cluster"
network.host: 0.0.0.0

#----------------------- BEGIN SECURITY AUTO CONFIGURATION -----------------------
#
# The following settings, TLS certificates, and keys have been automatically      
# generated to configure Elasticsearch security features on 20-10-2022 02:42:27
#
# --------------------------------------------------------------------------------

# Enable security features
xpack.security.enabled: true

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



##### 进入kibana容器内部

```sh
docker ps
docker exec -it kib  /bin/bash

./bin/kibana-keystore create

./bin/kibana-keystore add elasticsearch.username
# 账号:kibana_system

./bin/kibana-keystore add elasticsearch.password
# 密码:kibana_system设置的密码
```



##### 安装IK分词器

```sh
docker exec -it es容器ID /bin/bash

./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.1.2/elasticsearch-analysis-ik-8.1.2.zip

docker restart es
```
