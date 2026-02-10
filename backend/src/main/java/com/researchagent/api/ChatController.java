package com.researchagent.api;

import com.researchagent.api.dto.ChatRequest;
import com.researchagent.api.dto.ErrorResponse;
import com.researchagent.api.dto.MessageDto;
import com.researchagent.service.SessionService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class ChatController {

    private final ChatClient chatClient;
    private final SessionService sessionService;

    public ChatController(ChatClient chatClient, SessionService sessionService) {
        this.chatClient = chatClient;
        this.sessionService = sessionService;
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

        String sessionId = request.getSessionId();
        String userMessage = request.getMessage();

        // 保存用户消息
        if (sessionId != null && !sessionId.trim().isEmpty()) {
            MessageDto userMsg = new MessageDto();
            userMsg.setRole("user");
            userMsg.setContent(userMessage);
            sessionService.addMessage(sessionId, userMsg);
        }

        try {
            AtomicReference<StringBuilder> assistantContentRef = new AtomicReference<>(new StringBuilder());
            
            return chatClient.prompt()
                    .user(userMessage)
                    .stream()
                    .content()
                    .doOnNext(content -> {
                        // 累积助手回复内容
                        assistantContentRef.get().append(content);
                    })
                    .map(content -> {
                        // SSE 格式：event: message\ndata: {content}\n\n
                        return "event: message\n" + "data: " + escapeJson(content) + "\n\n";
                    })
                    .concatWith(Flux.defer(() -> {
                        // 流结束后保存助手消息
                        String fullContent = assistantContentRef.get().toString();
                        if (sessionId != null && !sessionId.trim().isEmpty() && !fullContent.isEmpty()) {
                            MessageDto assistantMsg = new MessageDto();
                            assistantMsg.setRole("assistant");
                            assistantMsg.setContent(fullContent);
                            sessionService.addMessage(sessionId, assistantMsg);
                        }
                        return Flux.just("event: done\n", "data: {}\n\n");
                    }))
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
