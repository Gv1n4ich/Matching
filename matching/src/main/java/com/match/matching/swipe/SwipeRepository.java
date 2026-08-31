package com.match.matching.swipe;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SwipeRepository extends JpaRepository<Swipe, Long> {
    boolean existsByFromProfileIdAndToProfileId(Long fromProfileId, Long toProfileId);
    Optional<Swipe> findByFromProfileIdAndToProfileIdAndType(Long fromProfileId, Long toProfileId, Swipe.SwipeType type);
}