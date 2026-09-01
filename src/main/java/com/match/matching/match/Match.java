package com.match.matching.match;

import com.match.matching.profile.Profile;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile1_id", nullable = false)
    private Profile profile1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile2_id", nullable = false)
    private Profile profile2;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Match() {}

    public Match(Profile profile1, Profile profile2) {
        this.profile1 = profile1;
        this.profile2 = profile2;
    }

    public Long getId() { return id; }
    public Profile getProfile1() { return profile1; }
    public Profile getProfile2() { return profile2; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}