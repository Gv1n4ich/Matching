package com.match.matching.chat;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Long senderId, MessageRequest dto) {
        Message message = new Message(
                dto.getMatchId(),
                senderId,
                dto.getRecipientId(),
                dto.getContent()
        );
        return messageRepository.save(message);
    }

    public List<Message> getChatHistory(Long matchId) {
        return messageRepository.findByMatchIdOrderByCreatedAtAsc(matchId);
    }
}