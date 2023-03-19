# 使用官方的 OpenJDK 镜像作为基础镜像
FROM openjdk:11-jre-slim

# 设置工作目录
WORKDIR /app

# 设置环境变量，指定存储应用程序的目录
ENV APP_HOME=/app

# 复制构建产物（JAR 文件）到工作目录
COPY target/*.jar $APP_HOME/app.jar

# 暴露容器的端口，与 Spring Boot 应用程序的端口保持一致
EXPOSE 8080

# 以前台方式启动 Spring Boot 应用程序
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
