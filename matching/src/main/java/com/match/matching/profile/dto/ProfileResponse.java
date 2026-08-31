package com.match.matching.profile.dto;

public record ProfileResponse(
        Long id,
        String email,
        String name,
        Integer age,
        String bio,
        String location,
        String avatar,
        String githubUrl,
        String linkedinUrl,
        String experience
) {}