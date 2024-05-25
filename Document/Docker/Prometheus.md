# Prometheus

1. 拉取镜像

   ```shell
   docker pull prom/prometheus:v2.52.0
   ```

2. 运行容器

   ```shell
   docker run -d \
   -p 9090:9090 \
   -v /root/docker/prometheus/config/prometheus.yml:/etc/prometheus/prometheus.yml \
   --name prometheus \
   prom/prometheus:v2.52.0
   ```

3. 访问Web界面

   http://localhost:9090