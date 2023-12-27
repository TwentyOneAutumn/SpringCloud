# Canal

1. 获取Canal镜像

   ```shell
   docker pull canal/canal-server:v1.1.7
   ```

2. 启动Canal

   ```shell
   docker run -d --name canal \
   -p 11111:11111 \
   -v /root/canal/instance.properties:/home/admin/canal-server/conf/example/instance.properties \
   canal/canal-server:v1.1.7 
   ```

   

3. xxx