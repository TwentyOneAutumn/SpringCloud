# 基础镜像
FROM adoptopenjdk:8u292-b10-jdk-hotspot

# 作者
MAINTAINER MapleJ

# Copy 服务Jar包到容器中
COPY target/test-1.0.jar test-1.0.jar

# 定义端口
EXPOSE 8802

# 定义启动命令
ENTRYPOINT ["java","-jar","basic-1.0.jar"]