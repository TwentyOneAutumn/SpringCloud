### ActiveMq



1.拉取ActiveMq镜像

```shell
docker pull islandora/activemq:main
```

2.启动ActiveMq

```shell
docker run -d \
-p 8161:8161 \
-p 61616:61616 \
-e ACTIVEMQ_USER=root \
-e ACTIVEMQ_PASSWORD=2762581@com \
--name activemq \
--restart=always \
islandora/activemq:main
```



---

