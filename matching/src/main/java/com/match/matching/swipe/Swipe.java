package com.match.matching.swipe;

import com.match.matching.profile.Profile;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "swipes")
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_profile_id", nullable = false)
    private Profile fromProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_profile_id", nullable = false)
    private Profile toProfile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwipeType type;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum SwipeType { LIKE, DISLIKE }

    public Swipe() {}

    public Swipe(Profile fromProfile, Profile toProfile, SwipeType type) {
        this.fromProfile = fromProfile;
        this.toProfile = toProfile;
        this.type = type;
    }

    public Long getId() { return id; }
    public Profile getFromProfile() { return fromProfile; }
    public Profile getToProfile() { return toProfile; }
    public SwipeType getType() { return type; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}