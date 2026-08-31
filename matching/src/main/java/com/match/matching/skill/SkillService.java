package com.match.matching.skill;

import com.match.matching.profile.Profile;
import com.match.matching.profile.ProfileRepository;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public SkillService(SkillRepository skillRepository, ProfileRepository profileRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Set<Skill> addSkillToUser(String email, String skillName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        String cleanName = skillName.trim();
        Skill skill = skillRepository.findByNameIgnoreCase(cleanName)
                .orElseGet(() -> skillRepository.save(new Skill(cleanName)));

        profile.getSkills().add(skill);
        return profileRepository.save(profile).getSkills();
    }

    @Transactional
    public Set<Skill> removeSkillFromUser(String email, Long skillId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        profile.getSkills().removeIf(skill -> skill.getId().equals(skillId));
        return profileRepository.save(profile).getSkills();
    }
}