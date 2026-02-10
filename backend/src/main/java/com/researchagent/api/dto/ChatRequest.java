package com.researchagent.api.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private String sessionId; // 可选，用于会话管理
}
