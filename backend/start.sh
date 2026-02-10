#!/bin/bash
cd "$(dirname "$0")"

# 检查 Java 环境
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

# 设置环境变量
export OPENAI_API_KEY=sk-ef97432b10f64e0e8401639744c71073
export OPENAI_BASE_URL=https://api.deepseek.com/v1
export OPENAI_MODEL=deepseek-chat

# 使用 Spring Boot Maven 插件运行（如果已编译）
# 或者提示用户需要先编译
if [ -f "target/research-agent-backend-1.0.0-SNAPSHOT.jar" ]; then
    echo "Starting backend server..."
    java -jar target/research-agent-backend-1.0.0-SNAPSHOT.jar
else
    echo "JAR file not found. Please compile first with: mvn clean package -DskipTests"
    echo "Or install Maven and run: mvn spring-boot:run"
    exit 1
fi
