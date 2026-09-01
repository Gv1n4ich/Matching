package com.match.matching.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void testGetHistory() throws Exception {
        Long matchId = 100L;

        Message message = new Message(matchId, 1L, 2L, "Привет! Как дела?");
        messageRepository.save(message);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/messages/" + matchId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Привет! Как дела?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].matchId").value(matchId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].senderId").value(1));
    }
}