# Research Agent - 营营无忧门店智能体

基于 Spring AI 2.0 和 React + Vite 的智能体项目，提供门店经营相关的智能对话能力。

## 项目结构

```
research-agent/
├── backend/          # Spring Boot 后端
├── frontend/         # React + Vite 前端
└── docs/            # 项目文档
```

## 技术栈

### 后端
- Java 21+
- Spring Boot 3.4.0
- Spring AI 1.0.0-M4
- Spring WebFlux (SSE 流式响应)

### 前端
- React 19
- TypeScript
- Vite 7
- React Router

## 快速开始

### 后端启动

1. 配置环境变量：
   ```bash
   cd backend
   cp .env.example .env
   # 编辑 .env，填入 OPENAI_API_KEY
   ```

2. 启动后端：
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   
   后端将在 http://localhost:8080 启动

### 前端启动

1. 安装依赖：
   ```bash
   cd frontend
   npm install
   ```

2. 启动开发服务器：
   ```bash
   npm run dev
   ```
   
   前端将在 http://localhost:5173 启动

## 功能特性

- ✅ 首页操作指引
- ✅ 智能对话（流式响应）
- ✅ Mock 工具集成
  - 门店经营摘要 (`get_store_summary`)
  - 金蝶凭证差异 (`get_finance_diff`)
- ✅ 会话历史管理
- ✅ 深色主题 UI

## API 接口

### 健康检查
```
GET /v1/health
```

### 流式对话
```
POST /v1/chat
Content-Type: application/json

{
  "message": "今天卖得怎么样？",
  "sessionId": "optional-session-id"
}

响应: text/event-stream
event: message
data: "回复内容"

event: done
data: {}
```

## 开发计划

- [x] Phase 1: Discovery - 需求提炼
- [x] Phase 2: Codebase Exploration - 项目搭建
- [x] Phase 3: Clarifications - 澄清决策
- [x] Phase 4: Architecture Design - 架构设计
- [x] Phase 5: 后端实现
- [x] Phase 6: 前端实现
- [ ] Phase 7: 联调测试与质量审查

## 文档

详细文档请查看 `docs/feature-dev/research-agent/` 目录：
- `01-discovery.md` - 需求发现
- `02-codebase-exploration.md` - 代码库探索
- `03-clarifications.md` - 澄清与决策
- `04-architecture.md` - 架构设计

## 许可证

MIT
