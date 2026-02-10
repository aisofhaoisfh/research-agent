# 快速启动指南

## 前置要求

- Java 21+
- Node.js 18+
- Maven 3.6+
- OpenAI API Key

## 启动步骤

### 1. 配置后端

```bash
cd backend
cp .env.example .env
# 编辑 .env，填入你的 OPENAI_API_KEY
```

### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动

### 3. 配置前端（可选）

如果需要修改后端地址：

```bash
cd frontend
cp .env.example .env
# 编辑 .env，修改 VITE_API_BASE（默认已配置代理，通常不需要修改）
```

### 4. 启动前端

```bash
cd frontend
npm install  # 首次运行需要
npm run dev
```

前端将在 http://localhost:5173 启动

## 验证

1. 访问 http://localhost:5173
2. 点击"创建对话"按钮
3. 发送消息测试对话功能
4. 尝试快捷提问，如"今天卖得怎么样？"

## 常见问题

### 后端启动失败

- 检查 Java 版本：`java -version`（需要 21+）
- 检查环境变量：确保 `.env` 文件存在且包含 `OPENAI_API_KEY`
- 检查端口占用：确保 8080 端口未被占用

### 前端无法连接后端

- 检查后端是否启动：访问 http://localhost:8080/v1/health
- 检查 CORS 配置：确保后端允许前端来源
- 检查代理配置：查看 `vite.config.ts` 中的代理设置

### 对话无响应

- 检查 OpenAI API Key 是否有效
- 查看后端日志，检查是否有错误
- 检查网络连接

## 下一步

- 查看 [架构文档](./04-architecture.md) 了解系统设计
- 查看 [质量审查](./05-quality-review.md) 了解代码质量
- 查看 [最终总结](./07-summary.md) 了解项目状态
