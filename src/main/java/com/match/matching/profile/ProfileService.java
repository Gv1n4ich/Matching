package com.match.matching.profile;

import com.match.matching.profile.dto.ProfileResponse;
import com.match.matching.profile.dto.UpdateProfileRequest;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProfileResponse getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + email));

        Profile profile = profileRepository.findByUser(user)
                .orElseGet(() -> createDefaultProfile(user));

        return mapToResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + email));

        Profile profile = profileRepository.findByUser(user)
                .orElseGet(() -> createDefaultProfile(user));

        if (request.name() != null) profile.setName(request.name());
        if (request.age() != null) profile.setAge(request.age());
        if (request.bio() != null) profile.setBio(request.bio());
        if (request.location() != null) profile.setLocation(request.location());
        if (request.experience() != null) profile.setExperience(request.experience());

        Profile savedProfile = profileRepository.save(profile);
        return mapToResponse(savedProfile);
    }

    private Profile createDefaultProfile(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName("Новый пользователь");
        return profileRepository.save(profile);
    }

    private ProfileResponse mapToResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getUser().getEmail(),
                profile.getName(),
                profile.getAge(),
                profile.getBio(),
                profile.getLocation(),
                null, // avatar
                null, // githubUrl
                null, // linkedinUrl
                profile.getExperience()
        );
    }
}