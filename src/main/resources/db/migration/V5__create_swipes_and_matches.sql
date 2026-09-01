CREATE TABLE swipes (
    id BIGSERIAL PRIMARY KEY,
    from_profile_id BIGINT NOT NULL,
    to_profile_id BIGINT NOT NULL,
    type VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_swipes_from_profile FOREIGN KEY (from_profile_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_swipes_to_profile FOREIGN KEY (to_profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE matches (
    id BIGSERIAL PRIMARY KEY,
    profile1_id BIGINT NOT NULL,
    profile2_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_matches_profile1 FOREIGN KEY (profile1_id) REFERENCES profiles(id) ON DELETE CASCADE,
    CONSTRAINT fk_matches_profile2 FOREIGN KEY (profile2_id) REFERENCES profiles(id) ON DELETE CASCADE
);