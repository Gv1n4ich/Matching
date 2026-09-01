package com.match.matching.skill;

import com.match.matching.profile.Profile;
import com.match.matching.profile.ProfileRepository;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/profile/skills")
public class SkillController {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillController(ProfileRepository profileRepository, SkillRepository skillRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Set<Skill>> addSkill(Principal principal, @RequestBody String skillName) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Profile profile = profileRepository.findByUser(user).orElseThrow();

        Skill skill = skillRepository.findByNameIgnoreCase(skillName.trim())
                .orElseGet(() -> skillRepository.save(new Skill(skillName.trim())));

        profile.getSkills().add(skill);
        profileRepository.save(profile);

        return ResponseEntity.ok(profile.getSkills());
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Set<Skill>> removeSkill(Principal principal, @PathVariable Long skillId) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Profile profile = profileRepository.findByUser(user).orElseThrow();

        profile.getSkills().removeIf(s -> s.getId().equals(skillId));
        profileRepository.save(profile);

        return ResponseEntity.ok(profile.getSkills());
    }
}