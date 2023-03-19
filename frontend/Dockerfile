# 使用官方的 Node.js 镜像作为基础镜像
FROM node:14 AS build-stage

# 设置工作目录
WORKDIR /app

# 将 package.json 和 package-lock.json 文件复制到工作目录
COPY package*.json ./

# 安装项目依赖
RUN npm install

# 复制项目源代码到工作目录
COPY . .

# 构建项目
RUN npm run build

# 使用官方的 Nginx 镜像作为基础镜像，用于托管静态文件
FROM nginx:stable-alpine as production-stage

# 将构建的静态文件复制到 Nginx 镜像的默认目录
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 暴露容器的 80 端口
EXPOSE 80

# 以前台方式启动 Nginx 服务
CMD ["nginx", "-g", "daemon off;"]
