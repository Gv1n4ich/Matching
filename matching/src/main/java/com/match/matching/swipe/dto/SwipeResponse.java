package com.match.matching.swipe.dto;

public class SwipeResponse {
    private boolean isMatch;
    private String message;

    public SwipeResponse(boolean isMatch, String message) {
        this.isMatch = isMatch;
        this.message = message;
    }

    public boolean isMatch() { return isMatch; }
    public String getMessage() { return message; }
}