# Docker deploy Jenkins

## Jenkins

1. 拉取镜像

   ```shell
   docker pull jenkins/jenkins:2.461
   ```

2. 启动镜像

   ```shell
   docker run -d \
   -p 8080:8080 \
   -p 50000:50000 \
   --name jenkins \
   jenkins/jenkins:2.461
   ```

3. 配置镜像加速

4. 下载中文插件

5. 重启

   ```shell
   docker restart jenkins
   ```
