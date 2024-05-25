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
   # 进入配置目录
   cd ./conf
   
   # 编辑配置文件
   vim defaults.ini
   
   # 修改前
   default_language = en-US
   
   # 修改后
   default_language = zh-Hans
   ```

5. 重新运行容器

   ```shell
   docker stop grafana && docker rm grafana
   
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

