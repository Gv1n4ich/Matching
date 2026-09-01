package com.match.matching.project;

import com.match.matching.profile.Profile;
import com.match.matching.profile.ProfileRepository;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, ProfileRepository profileRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> getUserProjects(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        return projectRepository.findByProfileId(profile.getId());
    }

    @Transactional
    public Project createProject(String email, Project project) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        project.setProfile(profile);
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
}