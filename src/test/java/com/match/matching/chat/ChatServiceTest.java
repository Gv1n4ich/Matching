package com.match.matching.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    void testSaveMessage() {
        Long senderId = 1L;
        MessageRequest request = new MessageRequest(10L, 2L, "Hello world");

        Message expectedMessage = new Message(10L, senderId, 2L, "Hello world");
        Mockito.when(messageRepository.save(ArgumentMatchers.any(Message.class))).thenReturn(expectedMessage);

        Message savedMessage = chatService.saveMessage(senderId, request);

        Assertions.assertNotNull(savedMessage);
        Assertions.assertEquals("Hello world", savedMessage.getContent());
        Assertions.assertEquals(senderId, savedMessage.getSenderId());
        Mockito.verify(messageRepository).save(ArgumentMatchers.any(Message.class));
    }
}