package com.researchagent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.researchagent.api.dto.SessionRequest;
import com.researchagent.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateSession() throws Exception {
        com.researchagent.api.dto.SessionResponse mockResponse = 
            new com.researchagent.api.dto.SessionResponse(
                "test-id", "测试会话", null, null, 0
            );
        
        when(sessionService.createSession(any())).thenReturn(mockResponse);
        
        SessionRequest request = new SessionRequest();
        request.setTitle("测试会话");
        
        mockMvc.perform(post("/v1/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.title").value("测试会话"));
    }

    @Test
    void testListSessions() throws Exception {
        com.researchagent.api.dto.SessionResponse session1 = 
            new com.researchagent.api.dto.SessionResponse("id1", "会话1", null, null, 0);
        com.researchagent.api.dto.SessionResponse session2 = 
            new com.researchagent.api.dto.SessionResponse("id2", "会话2", null, null, 0);
        
        when(sessionService.listSessions()).thenReturn(List.of(session1, session2));
        
        mockMvc.perform(get("/v1/sessions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[1].id").value("id2"));
    }

    @Test
    void testDeleteSession() throws Exception {
        when(sessionService.deleteSession("test-id")).thenReturn(true);
        
        mockMvc.perform(delete("/v1/sessions/test-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testDeleteNonExistentSession() throws Exception {
        when(sessionService.deleteSession("non-existent")).thenReturn(false);
        
        mockMvc.perform(delete("/v1/sessions/non-existent"))
                .andExpect(status().isNotFound());
    }
}
