# Docker deploy Redis

### Redis

1. 拉取Redis镜像 

   ```shell
   docker pull redis:7.0
   ```

1. 单机启动

1. 集群启动

   ```shell
   docker run -d \
   -p 6379:6379 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-master \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6379 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --dir /home/redis/data \
   --appendfilename RedisMasterAof.aof
   ```

   ```shell
   docker run -d \
   -p 6380:6380 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-slave1 \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6380 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --slaveof redis-master 6379
   --dir /home/redis/data \
   --appendfilename RedisSlaveOneAof.aof
   ```

   ```shell
   docker run -d \
   -p 6381:6381 \
   -v /home/redis/config/:/home/redis/config
   -v /home/redis/data/:/home/redis/data
   --name redis-slave2 \
   --restart=always \ 
   redis:7.0 \
   redis-server \
   /home/redis/config/redis.conf \
   --port 6381 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --requirepass root \
   --masterauth root \
   --slaveof redis-master 6379
   --dir /home/redis/data \
   --appendfilename RedisSlaveTwoAof.aof
   ```

   ```shell
   docker run -d \
   -p 6379:6379 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel1 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26379 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   ```shell
   docker run -d \
   -p 26380:26380 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel2 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26380 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   ```shell
   docker run -d \
   -p 26381:26381 \
   -v /home/redis/config/:/home/redis/config
   --name redis-sentinel3 \
   --restart=always \ 
   redis:7.0 \
   redis-sentinel \
   /home/redis/config/sentinel.conf \
   --port 26381 \
   --bind 0.0.0.0 \
   --protected-mode no \
   --sentinel resolve-hostnames yes \
   --sentinel monitor redis-master redis-master 6379 2 \
   --sentinel down-after-milliseconds redis-master 2000 \
   --sentinel failover-timeout redis-master 2000 \
   --sentinel auth-pass redis-master root
   ```

   

---

