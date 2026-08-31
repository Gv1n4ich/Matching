package com.match.matching.profile.dto;

public record UpdateProfileRequest(
        String name,
        Integer age,
        String bio,
        String location,
        String avatar,
        String githubUrl,
        String linkedinUrl,
        String experience
) {}