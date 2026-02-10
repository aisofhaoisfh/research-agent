package com.researchagent.service;

import com.researchagent.api.dto.MessageDto;
import com.researchagent.api.dto.SessionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceMessageTest {

    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        sessionService = new SessionService();
    }

    @Test
    void testAddMessage() {
        SessionResponse session = sessionService.createSession("测试会话");
        
        MessageDto message = new MessageDto();
        message.setRole("user");
        message.setContent("测试消息");
        
        sessionService.addMessage(session.getId(), message);
        
        List<MessageDto> messages = sessionService.getMessages(session.getId());
        assertEquals(1, messages.size());
        assertEquals("user", messages.get(0).getRole());
        assertEquals("测试消息", messages.get(0).getContent());
        assertNotNull(messages.get(0).getId());
        assertNotNull(messages.get(0).getTimestamp());
    }

    @Test
    void testAutoGenerateTitle() {
        SessionResponse session = sessionService.createSession(null);
        
        MessageDto message = new MessageDto();
        message.setRole("user");
        message.setContent("这是一个很长的消息内容，应该被截断到30个字符");
        
        sessionService.addMessage(session.getId(), message);
        
        SessionResponse updated = sessionService.getSession(session.getId()).orElseThrow();
        assertTrue(updated.getTitle().length() <= 33); // 30 + "..."
        assertTrue(updated.getTitle().endsWith("..."));
    }

    @Test
    void testGetMessages() {
        SessionResponse session = sessionService.createSession("测试会话");
        
        MessageDto msg1 = new MessageDto();
        msg1.setRole("user");
        msg1.setContent("消息1");
        sessionService.addMessage(session.getId(), msg1);
        
        MessageDto msg2 = new MessageDto();
        msg2.setRole("assistant");
        msg2.setContent("回复1");
        sessionService.addMessage(session.getId(), msg2);
        
        List<MessageDto> messages = sessionService.getMessages(session.getId());
        assertEquals(2, messages.size());
        assertEquals("user", messages.get(0).getRole());
        assertEquals("assistant", messages.get(1).getRole());
    }

    @Test
    void testClearMessages() {
        SessionResponse session = sessionService.createSession("测试会话");
        
        MessageDto message = new MessageDto();
        message.setRole("user");
        message.setContent("测试消息");
        sessionService.addMessage(session.getId(), message);
        
        assertEquals(1, sessionService.getMessages(session.getId()).size());
        
        sessionService.clearMessages(session.getId());
        
        assertEquals(0, sessionService.getMessages(session.getId()).size());
        
        SessionResponse updated = sessionService.getSession(session.getId()).orElseThrow();
        assertEquals(0, updated.getMessageCount());
    }

    @Test
    void testMessageCount() {
        SessionResponse session = sessionService.createSession("测试会话");
        
        MessageDto msg1 = new MessageDto();
        msg1.setRole("user");
        msg1.setContent("消息1");
        sessionService.addMessage(session.getId(), msg1);
        
        MessageDto msg2 = new MessageDto();
        msg2.setRole("assistant");
        msg2.setContent("回复1");
        sessionService.addMessage(session.getId(), msg2);
        
        SessionResponse updated = sessionService.getSession(session.getId()).orElseThrow();
        assertEquals(2, updated.getMessageCount());
    }
}
