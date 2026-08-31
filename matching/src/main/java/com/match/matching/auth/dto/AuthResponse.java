package com.match.matching.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}