# 应用启动状态

## 当前状态

### ✅ 前端服务
- **状态**: 已启动
- **端口**: 5173
- **访问地址**: http://localhost:5173
- **进程**: 运行中

### ⚠️ 后端服务
- **状态**: 需要编译启动
- **端口**: 8080
- **配置**: 已配置 DeepSeek API
  - API Key: sk-ef97432b10f64e0e8401639744c71073
  - Base URL: https://api.deepseek.com/v1
  - Model: deepseek-chat

## 启动后端

由于环境中没有 Maven，需要先安装 Maven 或使用其他方式编译：

### 方式一：安装 Maven 后启动
```bash
# 安装 Maven（Ubuntu/Debian）
sudo apt-get update
sudo apt-get install maven

# 启动后端
cd backend
mvn spring-boot:run
```

### 方式二：使用 Docker（如果有）
```bash
# 使用 Maven Docker 镜像编译
docker run -it --rm -v "$(pwd)/backend:/app" -w /app maven:3.9-eclipse-temurin-21 mvn spring-boot:run
```

### 方式三：在本地环境编译
1. 在本地安装 Maven
2. 编译项目：`cd backend && mvn clean package -DskipTests`
3. 上传 JAR 文件到服务器
4. 运行：`java -jar target/research-agent-backend-1.0.0-SNAPSHOT.jar`

## 访问应用

前端已启动，可以访问：
- **本地访问**: http://localhost:5173
- **如果使用远程服务器**: 需要配置端口转发或防火墙规则

## 下一步

1. 启动后端服务（需要 Maven 或已编译的 JAR）
2. 验证前后端连接
3. 测试对话功能

## 配置信息

所有配置已保存在：
- `backend/.env` - 环境变量
- `backend/src/main/resources/application.yml` - 应用配置

