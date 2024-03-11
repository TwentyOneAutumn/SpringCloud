# 基础镜像
FROM bitnami/java:1.8.402-7

# 作者
MAINTAINER MapleJ

# Copy Jar包到容器中
COPY target/docker-1.0.jar docker-1.0.jar

# 定义端口
EXPOSE 8888

# 定义启动命令
ENTRYPOINT ["java","-jar","docker-1.0.jar"]