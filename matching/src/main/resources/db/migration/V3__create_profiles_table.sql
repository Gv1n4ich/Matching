CREATE TABLE profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255),
    age INT,
    bio TEXT,
    location VARCHAR(255),
    avatar VARCHAR(512),
    github_url VARCHAR(255),
    linkedin_url VARCHAR(255),
    experience VARCHAR(255),
    CONSTRAINT fk_profiles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);