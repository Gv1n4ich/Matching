package com.match.matching.swipe;

import com.match.matching.match.Match;
import com.match.matching.match.MatchRepository;
import com.match.matching.profile.Profile;
import com.match.matching.profile.ProfileRepository;
import com.match.matching.swipe.dto.SwipeRequest;
import com.match.matching.swipe.dto.SwipeResponse;
import com.match.matching.user.User;
import com.match.matching.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SwipeService {

    private final SwipeRepository swipeRepository;
    private final MatchRepository matchRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public SwipeService(SwipeRepository swipeRepository, MatchRepository matchRepository,
                        ProfileRepository profileRepository, UserRepository userRepository) {
        this.swipeRepository = swipeRepository;
        this.matchRepository = matchRepository;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SwipeResponse processSwipe(String email, SwipeRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile fromProfile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        Profile toProfile = profileRepository.findById(request.getTargetProfileId())
                .orElseThrow(() -> new RuntimeException("Целевой профиль не найден"));

        if (fromProfile.getId().equals(toProfile.getId())) {
            throw new IllegalArgumentException("Нельзя свайпать самого себя");
        }

        if (swipeRepository.existsByFromProfileIdAndToProfileId(fromProfile.getId(), toProfile.getId())) {
            return new SwipeResponse(false, "Вы уже свайпали этот профиль");
        }

        Swipe swipe = new Swipe(fromProfile, toProfile, request.getType());
        swipeRepository.save(swipe);

        if (request.getType() == Swipe.SwipeType.LIKE) {
            boolean isMutual = swipeRepository.findByFromProfileIdAndToProfileIdAndType(
                    toProfile.getId(), fromProfile.getId(), Swipe.SwipeType.LIKE).isPresent();

            if (isMutual) {
                matchRepository.save(new Match(fromProfile, toProfile));
                return new SwipeResponse(true, "It's a Match! 🎉");
            }
        }

        return new SwipeResponse(false, "Свайп сохранен");
    }

    @Transactional(readOnly = true)
    public List<Match> getUserMatches(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Профиль не найден"));

        return matchRepository.findAllByProfileId(profile.getId());
    }
}