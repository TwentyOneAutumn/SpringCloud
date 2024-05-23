# Minio

1. 拉取Minio镜像

   ```shell
   docker pull bitnami/minio:2023
   docker pull minio/minio:RELEASE.2023-09-04T19-57-37Z 
   ```

2. 单机部署

   ```shell
   docker run -d \
   -p 9000:9000 \
   -p 9001:9001 \
   -e MINIO_ROOT_USER=root \
   -e MINIO_ROOT_PASSWORD=2762581@com \
   -e MINIO_DISTRIBUTED_MODE_ENABLED=yes \
   -e MINIO_DISTRIBUTED_NODES=minio1,minio2,minio3,minio4 \
   --name minio \
   bitnami/minio:2023
   ```

   ```shell
   docker run -d \
   -p 9000:9000 \
   -p 9001:9001 \
   -e MINIO_ROOT_USER=root \
   -e MINIO_ROOT_PASSWORD=2762581@com \
   --name minio \
   minio/minio:RELEASE.2023-09-04T19-57-37Z \
   server \
   --console-address ":9001" \
   http://minio/data
   ```

3. 集群部署（docker-compose.yml）

   ```yaml
   version: '2'
   
   services:
     minio1:
       ports:
         - "9000:9000"
         - "9001:9001"
       image: 'bitnami/minio:2023'
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
         - MINIO_DISTRIBUTED_MODE_ENABLED=yes
         - MINIO_DISTRIBUTED_NODES=minio1,minio2,minio3,minio4
         - MINIO_SKIP_CLIENT=yes
     minio2:
       ports:
         - "9002:9000"
         - "9003:9001"
       image: 'bitnami/minio:2023'
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
         - MINIO_DISTRIBUTED_MODE_ENABLED=yes
         - MINIO_DISTRIBUTED_NODES=minio1,minio2,minio3,minio4
         - MINIO_SKIP_CLIENT=yes
     minio3:
       ports:
         - "9004:9000"
         - "9005:9001"
       image: 'bitnami/minio:2023'
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
         - MINIO_DISTRIBUTED_MODE_ENABLED=yes
         - MINIO_DISTRIBUTED_NODES=minio1,minio2,minio3,minio4
         - MINIO_SKIP_CLIENT=yes
     minio4:
       ports:
         - "9006:9000"
         - "9007:9001"
       image: 'bitnami/minio:2023'
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
         - MINIO_DISTRIBUTED_MODE_ENABLED=yes
         - MINIO_DISTRIBUTED_NODES=minio1,minio2,minio3,minio4
         - MINIO_SKIP_CLIENT=yes
   
   ```

   ```yaml
   version: '2'
   
   services:
     minio0:
       image: 'minio/minio:RELEASE.2023-09-04T19-57-37Z'
       ports:
         - "9000:9000"
         - "9001:9001"
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
       command: [
         server,
         --console-address,
         ":9001",
         "http://minio{0...3}/data"
       ]
     minio1:
       image: 'minio/minio:RELEASE.2023-09-04T19-57-37Z'
       ports:
         - "9002:9000"
         - "9003:9001"
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
       command: [
         server,
         --console-address,
         ":9001",
         "http://minio{0...3}/data"
       ]
     minio2:
       image: 'minio/minio:RELEASE.2023-09-04T19-57-37Z'
       ports:
         - "9004:9000"
         - "9005:9001"
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
       command: [
         server,
         --console-address,
         ":9001",
         "http://minio{0...3}/data"
       ]
     minio3:
       image: 'minio/minio:RELEASE.2023-09-04T19-57-37Z'
       ports:
         - "9006:9000"
         - "9007:9001"
       environment:
         - MINIO_ROOT_USER=root
         - MINIO_ROOT_PASSWORD=2762581@com
       command: [
         server,
         --console-address,
         ":9001",
         "http://minio{0...3}/data"
       ]
   ```

   

------

