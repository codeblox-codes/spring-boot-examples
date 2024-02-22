package com.codeblox.springsecurityfullstack.repository.security;

import com.codeblox.springsecurityfullstack.entity.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken>findByToken(String token);

    Optional<RefreshToken> findByUserUsername(String username);
}
