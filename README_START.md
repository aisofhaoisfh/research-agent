# 快速启动指南

## 配置说明

已配置 DeepSeek API：
- API Key: `sk-ef97432b10f64e0e8401639744c71073`
- Base URL: `https://api.deepseek.com/v1`
- Model: `deepseek-chat`

## 启动步骤

### 1. 启动后端

**方式一：使用 Maven（推荐）**
```bash
cd backend
mvn spring-boot:run
```

**方式二：编译后运行**
```bash
cd backend
mvn clean package -DskipTests
java -jar target/research-agent-backend-1.0.0-SNAPSHOT.jar
```

**方式三：使用启动脚本**
```bash
cd backend
./start.sh
```

后端将在 http://localhost:8080 启动

### 2. 启动前端

```bash
cd frontend
npm install  # 首次运行需要
npm run dev
```

前端将在 http://localhost:5173 启动

### 3. 访问应用

打开浏览器访问：http://localhost:5173

## 验证

1. 检查后端健康状态：
   ```bash
   curl http://localhost:8080/v1/health
   ```

2. 检查前端是否运行：
   访问 http://localhost:5173

## 当前状态

- ✅ 前端已启动（端口 5173）
- ⚠️ 后端需要编译后启动（端口 8080）

## 注意事项

1. 确保 Java 21+ 已安装
2. 确保 Maven 已安装（用于编译后端）
3. 确保 Node.js 18+ 已安装（用于运行前端）
4. 后端需要先编译才能运行

## 故障排查

### 后端无法启动
- 检查 Java 版本：`java -version`（需要 21+）
- 检查 Maven：`mvn -version`
- 查看日志：`backend/backend.log`

### 前端无法启动
- 检查 Node.js：`node -version`（需要 18+）
- 重新安装依赖：`cd frontend && npm install`
- 查看日志：`frontend/frontend.log`

### API 连接失败
- 检查后端是否运行：`curl http://localhost:8080/v1/health`
- 检查 CORS 配置
- 检查网络连接
