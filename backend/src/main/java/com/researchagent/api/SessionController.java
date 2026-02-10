package com.researchagent.api;

import com.researchagent.api.dto.ErrorResponse;
import com.researchagent.api.dto.SessionRequest;
import com.researchagent.api.dto.SessionResponse;
import com.researchagent.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/sessions")
@CrossOrigin
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody(required = false) SessionRequest request) {
        String title = request != null ? request.getTitle() : null;
        SessionResponse session = sessionService.createSession(title);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> listSessions() {
        List<SessionResponse> sessions = sessionService.listSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSession(@PathVariable String id) {
        Optional<SessionResponse> session = sessionService.getSession(id);
        if (session.isPresent()) {
            return ResponseEntity.ok(session.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable String id) {
        if (sessionService.deleteSession(id)) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable String id, @RequestBody SessionRequest request) {
        sessionService.updateSession(id, request != null ? request.getTitle() : null);
        Optional<SessionResponse> session = sessionService.getSession(id);
        if (session.isPresent()) {
            return ResponseEntity.ok(session.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "会话不存在"));
        }
    }
}
