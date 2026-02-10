package com.researchagent.service;

import com.researchagent.api.dto.MessageDto;
import com.researchagent.api.dto.SessionResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<String, SessionData> sessions = new ConcurrentHashMap<>();

    public SessionResponse createSession(String title) {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        
        SessionData session = new SessionData();
        session.id = id;
        session.title = title != null && !title.trim().isEmpty() ? title : "新对话";
        session.createdAt = now;
        session.updatedAt = now;
        session.messageCount = 0;
        
        sessions.put(id, session);
        
        return toResponse(session);
    }

    public List<SessionResponse> listSessions() {
        return sessions.values().stream()
                .sorted((a, b) -> b.updatedAt.compareTo(a.updatedAt))
                .map(this::toResponse)
                .toList();
    }

    public Optional<SessionResponse> getSession(String id) {
        SessionData session = sessions.get(id);
        return session != null ? Optional.of(toResponse(session)) : Optional.empty();
    }

    public boolean deleteSession(String id) {
        return sessions.remove(id) != null;
    }

    public void updateSession(String id, String title) {
        SessionData session = sessions.get(id);
        if (session != null) {
            if (title != null && !title.trim().isEmpty()) {
                session.title = title;
            }
            session.updatedAt = LocalDateTime.now();
        }
    }

    public void incrementMessageCount(String id) {
        SessionData session = sessions.get(id);
        if (session != null) {
            session.messageCount++;
            session.updatedAt = LocalDateTime.now();
        }
    }

    public void addMessage(String sessionId, MessageDto message) {
        SessionData session = sessions.get(sessionId);
        if (session != null) {
            if (session.messages == null) {
                session.messages = new ArrayList<>();
            }
            message.setId(UUID.randomUUID().toString());
            message.setTimestamp(LocalDateTime.now());
            session.messages.add(message);
            session.messageCount = session.messages.size();
            session.updatedAt = LocalDateTime.now();
            
            // 自动生成会话标题（第一条用户消息）
            if (session.messages.size() == 1 && "user".equals(message.getRole())) {
                String title = message.getContent();
                if (title.length() > 30) {
                    title = title.substring(0, 30) + "...";
                }
                session.title = title;
            }
        }
    }

    public List<MessageDto> getMessages(String sessionId) {
        SessionData session = sessions.get(sessionId);
        if (session != null && session.messages != null) {
            return new ArrayList<>(session.messages);
        }
        return new ArrayList<>();
    }

    public void clearMessages(String sessionId) {
        SessionData session = sessions.get(sessionId);
        if (session != null) {
            if (session.messages != null) {
                session.messages.clear();
            }
            session.messageCount = 0;
            session.updatedAt = LocalDateTime.now();
        }
    }

    private SessionResponse toResponse(SessionData session) {
        return new SessionResponse(
                session.id,
                session.title,
                session.createdAt,
                session.updatedAt,
                session.messageCount
        );
    }

    private static class SessionData {
        String id;
        String title;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        int messageCount;
        List<MessageDto> messages;
    }
}
