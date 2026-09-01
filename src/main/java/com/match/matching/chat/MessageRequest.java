package com.match.matching.chat;

public class MessageRequest {
    private Long matchId;
    private Long recipientId;
    private String content;

    public MessageRequest() {
    }

    public MessageRequest(Long matchId, Long recipientId, String content) {
        this.matchId = matchId;
        this.recipientId = recipientId;
        this.content = content;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}