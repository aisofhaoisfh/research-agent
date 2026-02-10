# Phase 2: Codebase Exploration - 当前状态

## 项目现状

当前 [research-agent](../../../) 仅有 README，无前端/后端代码，**相当于从零搭建**。

## 规范与模式来源

### 前端规范

参考：[agent-scaffold/references/frontend-react-vite.md](references/frontend-react-vite.md)

- **技术栈**：React + Vite
- **目录约定**：
  - `pages/`：页面组件
  - `components/`：通用组件
  - `api/`：API服务
  - `hooks/`：自定义Hooks
- **环境变量**：`VITE_API_BASE` 指向后端Base URL
- **SSE/错误体契约**：统一的事件格式和错误处理
- **智能体对话UI规范**：
  - 消息区：消息列表展示
  - 输入区：消息输入框
  - 流式展示：逐字/逐块渲染
  - 连接状态：连接状态指示

### 后端规范

参考：Spring AI 2.0 最佳实践

- **技术栈**：Spring AI 2.0
- **核心组件**：
  - `ChatClient`：对话客户端
  - `WebFlux`：流式响应（SSE）
  - `SkillsTool`：工具注册
- **配置**：API Key 通过环境变量配置
- **不采用**：agent-scaffold 的 LangChain/FastAPI 后端

## 后续将建立的目录与约定

### 前端目录结构

```
frontend/
├── src/
│   ├── pages/
│   │   ├── HomeView.tsx
│   │   └── ChatView.tsx
│   ├── components/
│   │   ├── Layout/
│   │   │   ├── Sidebar.tsx
│   │   │   └── MainArea.tsx
│   │   ├── Chat/
│   │   │   ├── MessageList.tsx
│   │   │   ├── MessageBubble.tsx
│   │   │   └── ChatInput.tsx
│   │   └── ...
│   ├── api/
│   │   └── chat.ts
│   ├── hooks/
│   │   └── useChat.ts
│   ├── styles/
│   │   ├── variables.css
│   │   └── ...
│   └── App.tsx
├── vite.config.ts
└── package.json
```

### 后端目录结构

```
backend/
├── src/main/java/com/researchagent/
│   ├── api/
│   │   └── ChatController.java
│   ├── config/
│   │   ├── ChatClientConfig.java
│   │   └── CorsConfig.java
│   ├── tools/
│   │   ├── StoreSummaryTool.java
│   │   └── FinanceDiffTool.java
│   └── Application.java
├── src/main/resources/
│   └── application.yml
└── pom.xml / build.gradle
```

## 技术选型确认

### 前端

- React 18+
- Vite 5+
- TypeScript
- React Router（路由）

### 后端

- Java 21+
- Spring Boot 4.x
- Spring AI 2.0.0-M2+
- Spring WebFlux（SSE流式）

## 下一步

1. 创建文档目录结构
2. 搭建后端项目骨架
3. 搭建前端项目骨架
4. 实现核心功能
