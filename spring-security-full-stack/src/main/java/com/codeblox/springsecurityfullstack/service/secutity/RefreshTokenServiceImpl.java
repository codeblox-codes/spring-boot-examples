package com.codeblox.springsecurityfullstack.service.secutity;

import com.codeblox.springsecurityfullstack.entity.security.RefreshToken;
import com.codeblox.springsecurityfullstack.repository.security.RefreshTokenRepository;
import com.codeblox.springsecurityfullstack.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements IRefreshTokenServiceImpl {

    private RefreshTokenRepository refreshTokenRepository;

    private UserRepository userRepository;


    public RefreshToken createRefreshToken(String username){
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findByUserUsername(username);
        foundRefreshToken.ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("No user found for the username provided")))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }



    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token has expired. Login again..!");
        }
        return token;
    }

    public RefreshToken getRefreshTokenByUserName(String username){
        return refreshTokenRepository.findByUserUsername(username).orElseThrow(()->new RuntimeException("No refresh Token found"));
    }

    public void deleteToken(RefreshToken refreshTokenToDelete) {
        refreshTokenRepository.delete(refreshTokenToDelete);
    }
}
