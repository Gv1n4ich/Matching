package com.match.matching.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void processMessage(@Payload MessageRequest request, Principal principal) {
        Long senderId = Long.parseLong(principal.getName());
        Message saved = chatService.saveMessage(senderId, request);

        messagingTemplate.convertAndSend(
                "/topic/match/" + request.getMatchId(),
                saved
        );
    }

    @GetMapping("/api/messages/{matchId}")
    public ResponseEntity<List<Message>> getHistory(@PathVariable Long matchId) {
        return ResponseEntity.ok(chatService.getChatHistory(matchId));
    }
}