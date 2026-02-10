package com.researchagent.api;

import com.researchagent.api.dto.ErrorResponse;
import com.researchagent.api.dto.MessageDto;
import com.researchagent.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/sessions/{sessionId}/messages")
@CrossOrigin
public class MessageController {

    private final SessionService sessionService;

    public MessageController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getMessages(@PathVariable String sessionId) {
        // 验证会话存在
        if (!sessionService.getSession(sessionId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }

        List<MessageDto> messages = sessionService.getMessages(sessionId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<?> addMessage(
            @PathVariable String sessionId,
            @RequestBody MessageDto message) {
        // 验证会话存在
        if (!sessionService.getSession(sessionId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }

        // 验证消息内容
        if (message.getRole() == null || message.getContent() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("INVALID_REQUEST", "消息角色和内容不能为空"));
        }

        sessionService.addMessage(sessionId, message);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true));
    }

    @DeleteMapping
    public ResponseEntity<?> clearMessages(@PathVariable String sessionId) {
        // 验证会话存在
        if (!sessionService.getSession(sessionId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }

        sessionService.clearMessages(sessionId);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
