# 基础镜像
FROM adoptopenjdk:8u292-b10-jdk-hotspot

# 作者
MAINTAINER MapleJ

# Copy 服务Jar包到容器中
COPY target/basic-1.0.jar basic-1.0.jar

# Copy skywalking-agent.jar包到容器中
COPY target/basic-1.0.jar basic-1.0.jar

RUN bash -c "touch /basic-1.0.jar"

EXPOSE 8802

ENTRYPOINT [
    "java",
    "-javaagent:/path/to/skywalking-agent/skywalking-agent.jar=agent.service_name=basic",
    "-jar",
    "basic-1.0.jar"
]