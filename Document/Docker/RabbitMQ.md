### RabbitMQ

1. 拉取RabbitMQ镜像

   ```shell
   docker pull rabbitmq:latest
   ```

2. 启动RabbitMQ

   ```shell
   docker run -d \
   -p 15672:15672  \
   -p 5672:5672   \
   -e RABBITMQ_DEFAULT_USER=root  \
   -e RABBITMQ_DEFAULT_PASS=2762581@com  \
   -e RABBITMQ_MANAGEMENT=true \
   --name rabbitmq \
   --hostname rabbitmq \  
   --restart=always \
   rabbitmq:latest
   ```

3. 安装依赖

   ```sh
   docker exec -it rabbitmq /bin/bash # 进入RabbitMQ容器
   
   rabbitmq-plugins enable rabbitmq_management # 下载enable语言依赖
   
   cd /etc/rabbitmq/conf.d
   
   echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf
   
   exit # 退出容器
   
   docker restart rabbitmq # 重启RabbitMQ容器
   ```

   

---

