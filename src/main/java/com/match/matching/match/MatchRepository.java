package com.match.matching.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.profile1.id = :profileId OR m.profile2.id = :profileId")
    List<Match> findAllByProfileId(@Param("profileId") Long profileId);
}