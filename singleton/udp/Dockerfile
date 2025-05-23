# 基础镜像
FROM bitnami/java:1.8.402-7

# Copy 服务Jar包到容器中
COPY udp-1.0.jar udp-1.0.jar

# 定义端口
EXPOSE 8888

EXPOSE 8206/udp

# 定义启动命令
ENTRYPOINT ["java","-jar","udp-1.0.jar"]