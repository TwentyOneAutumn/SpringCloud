# Docker deploy MySQL

### MySQL

1. 拉取MySQL镜像

   ```shell
   docker pull mysql:8.0.32
   ```

2. 启动MySQL

   ```shell
   docker run -itd \
   --name mysql \
   -p 3306:3306 \
   -e MYSQL_ROOT_PASSWORD=2762581@com \
   --restart=always \
   mysql:8.0.32
   ```




---

