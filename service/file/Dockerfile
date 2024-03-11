# 基础镜像
FROM bitnami/java:1.8.402-7

# 作者
MAINTAINER MapleJ

# Copy 服务Jar包到容器中
COPY target/file-1.0.jar file-1.0.jar

# 定义端口
EXPOSE 8802

# 定义启动命令
ENTRYPOINT ["java","-jar","file-1.0.jar"]