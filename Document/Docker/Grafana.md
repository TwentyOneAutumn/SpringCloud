# Grafana

1. 拉取镜像

   ```shell
   docker pull grafana/grafana:11.0.0
   ```

2. 运行容器

   ```shell
   docker run --rm -d \
   -p 3000:3000 \
   -e GF_SECURITY_ADMIN_USER=root \
   -e GF_SECURITY_ADMIN_PASSWORD=2762581@com \
   --name=grafana \
   grafana/grafana:11.0.0
   ```

3. 复制配置文件

   ```shell
   docker cp grafana:/usr/share/grafana/conf ./conf
   ```

4. 编辑配置文件

   ```shell
   sed -i 's/default_language = en-US/default_language = zh-Hans/' ./conf/defaults.ini
   ```

5. 重新运行容器

   ```shell
   # 停止并删除旧容器
   docker stop grafana && docker rm grafana
   
   # 运行新容器
   docker run -d \
   -p 3000:3000 \
   -e GF_SECURITY_ADMIN_USER=root \
   -e GF_SECURITY_ADMIN_PASSWORD=2762581@com \
   -v ./conf/:/usr/share/grafana/conf/ \
   --name=grafana \
   grafana/grafana:11.0.0
   ```

6. 访问界面

   http://localhost:3000

