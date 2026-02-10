# 外部访问配置

## 当前状态

### ✅ 后端服务
- **绑定地址**: 0.0.0.0:8080 (已允许外部访问)
- **本地访问**: http://localhost:8080
- **外部访问**: http://<服务器IP>:8080

### ✅ 前端服务
- **绑定地址**: 0.0.0.0:5173 (已配置允许外部访问)
- **本地访问**: http://localhost:5173
- **外部访问**: http://<服务器IP>:5173

## 服务器信息

### 服务器 IP 地址
- **主 IP**: 172.30.0.2
- **备用 IP**: 172.17.0.1

### 访问地址

**前端界面**:
- http://172.30.0.2:5173
- http://172.17.0.1:5173

**后端 API**:
- http://172.30.0.2:8080/v1
- http://172.17.0.1:8080/v1

## 配置说明

### 后端配置
后端 Spring Boot 默认绑定到 `0.0.0.0`，已允许外部访问。

### 前端配置
已修改 `vite.config.ts`，添加 `host: '0.0.0.0'` 配置：
```typescript
server: {
  host: '0.0.0.0', // 允许外部访问
  port: 5173,
  ...
}
```

## 防火墙配置

如果无法从外部访问，可能需要配置防火墙规则：

### Ubuntu/Debian (ufw)
```bash
sudo ufw allow 8080/tcp
sudo ufw allow 5173/tcp
```

### CentOS/RHEL (firewalld)
```bash
sudo firewall-cmd --permanent --add-port=8080/tcp
sudo firewall-cmd --permanent --add-port=5173/tcp
sudo firewall-cmd --reload
```

### 云服务器安全组
如果使用云服务器（AWS、阿里云、腾讯云等），需要在安全组中开放：
- 端口 8080 (后端)
- 端口 5173 (前端)

## 验证外部访问

### 测试后端
```bash
curl http://172.30.0.2:8080/v1/health
# 应该返回: {"status":"ok","service":"research-agent-backend"}
```

### 测试前端
在浏览器中访问：
- http://172.30.0.2:5173

## 域名访问（可选）

如果需要使用域名访问，可以：

1. **配置 Nginx 反向代理**:
```nginx
server {
    listen 80;
    server_name your-domain.com;

    location / {
        proxy_pass http://localhost:5173;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location /v1 {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

2. **使用域名访问**:
- http://your-domain.com (前端)
- http://your-domain.com/v1 (后端 API)

## 注意事项

1. **安全性**: 
   - 生产环境建议使用 HTTPS
   - 配置适当的认证和授权
   - 限制 API 访问频率

2. **CORS 配置**:
   - 后端已配置 CORS，允许 localhost:5173 和 localhost:3000
   - 如果使用其他域名，需要更新 `CorsConfig.java`

3. **API Key 安全**:
   - 当前 API Key 在配置文件中，生产环境应使用环境变量或密钥管理服务

## 故障排查

### 无法访问
1. 检查服务是否运行: `ps aux | grep -E "spring-boot|vite"`
2. 检查端口监听: `netstat -tuln | grep -E "8080|5173"`
3. 检查防火墙: `sudo ufw status` 或 `sudo firewall-cmd --list-ports`
4. 检查安全组规则（云服务器）

### 连接超时
1. 确认服务器 IP 地址正确
2. 检查网络连接
3. 检查防火墙和安全组设置

---

**配置时间**: 2026-02-10  
**状态**: ✅ 已配置外部访问
