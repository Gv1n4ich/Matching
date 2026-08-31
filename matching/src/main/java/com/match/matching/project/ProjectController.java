package com.match.matching.project;

import com.match.matching.profile.Profile;
import com.match.matching.profile.ProfileRepository;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository, ProfileRepository profileRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getMyProjects(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Profile profile = profileRepository.findByUser(user).orElseThrow();
        return ResponseEntity.ok(projectRepository.findByProfileId(profile.getId()));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(Principal principal, @RequestBody Project project) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Profile profile = profileRepository.findByUser(user).orElseThrow();

        project.setProfile(profile);
        return ResponseEntity.ok(projectRepository.save(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(Principal principal, @PathVariable Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}