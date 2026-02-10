package com.researchagent.api;

import com.researchagent.api.dto.ChatRequest;
import com.researchagent.api.dto.ErrorResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "ok", "service", "research-agent-backend"));
    }

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody ChatRequest request) {
        if (request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", "消息内容不能为空");
            return Flux.just("event: error\n", "data: " + errorResponse.toJson() + "\n\n");
        }

        try {
            return chatClient.prompt()
                    .user(request.getMessage())
                    .stream()
                    .content()
                    .map(content -> {
                        // SSE 格式：event: message\ndata: {content}\n\n
                        return "event: message\n" + "data: " + escapeJson(content) + "\n\n";
                    })
                    .concatWith(Flux.just("event: done\n", "data: {}\n\n"))
                    .onErrorResume(error -> {
                        ErrorResponse errorResponse = new ErrorResponse(
                            "CHAT_ERROR",
                            error.getMessage() != null ? error.getMessage() : "未知错误"
                        );
                        return Flux.just("event: error\n", "data: " + errorResponse.toJson() + "\n\n");
                    });
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "CHAT_ERROR",
                e.getMessage() != null ? e.getMessage() : "未知错误"
            );
            return Flux.just("event: error\n", "data: " + errorResponse.toJson() + "\n\n");
        }
    }

    private String escapeJson(String content) {
        if (content == null) {
            return "null";
        }
        // 简单的 JSON 字符串转义
        return "\"" + content
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
    }
}
