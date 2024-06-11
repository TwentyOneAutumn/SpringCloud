# Docker deploy Jenkins

## Jenkins

1. 拉取镜像

   ```shell
   docker pull jenkins/jenkins:lts
   ```

2. 启动镜像

   ```shell
   docker run -d \
   -p 8080:8080 \
   -p 50000:50000 \
   --name jenkins \
   jenkins/jenkins:latest
   ```

   
