package com.researchagent.service;

import com.researchagent.api.dto.SessionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        sessionService = new SessionService();
    }

    @Test
    void testCreateSession() {
        SessionResponse session = sessionService.createSession("测试会话");
        
        assertNotNull(session);
        assertNotNull(session.getId());
        assertEquals("测试会话", session.getTitle());
        assertEquals(0, session.getMessageCount());
    }

    @Test
    void testCreateSessionWithoutTitle() {
        SessionResponse session = sessionService.createSession(null);
        
        assertNotNull(session);
        assertEquals("新对话", session.getTitle());
    }

    @Test
    void testListSessions() {
        sessionService.createSession("会话1");
        sessionService.createSession("会话2");
        
        List<SessionResponse> sessions = sessionService.listSessions();
        
        assertTrue(sessions.size() >= 2);
    }

    @Test
    void testGetSession() {
        SessionResponse created = sessionService.createSession("测试会话");
        
        var session = sessionService.getSession(created.getId());
        
        assertTrue(session.isPresent());
        assertEquals("测试会话", session.get().getTitle());
    }

    @Test
    void testGetNonExistentSession() {
        var session = sessionService.getSession("non-existent-id");
        
        assertFalse(session.isPresent());
    }

    @Test
    void testDeleteSession() {
        SessionResponse created = sessionService.createSession("测试会话");
        
        boolean deleted = sessionService.deleteSession(created.getId());
        
        assertTrue(deleted);
        assertFalse(sessionService.getSession(created.getId()).isPresent());
    }

    @Test
    void testUpdateSession() {
        SessionResponse created = sessionService.createSession("原始标题");
        
        sessionService.updateSession(created.getId(), "新标题");
        
        var updated = sessionService.getSession(created.getId());
        assertTrue(updated.isPresent());
        assertEquals("新标题", updated.get().getTitle());
    }

    @Test
    void testIncrementMessageCount() {
        SessionResponse created = sessionService.createSession("测试会话");
        
        sessionService.incrementMessageCount(created.getId());
        sessionService.incrementMessageCount(created.getId());
        
        var session = sessionService.getSession(created.getId());
        assertTrue(session.isPresent());
        assertEquals(2, session.get().getMessageCount());
    }
}
