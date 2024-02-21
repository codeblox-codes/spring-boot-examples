package com.codeblox.springsecurityfullstack.entity.security;

import com.codeblox.springsecurityfullstack.entity.BaseEntity;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {
    private String token;
    private Instant expiryDate;
    @OneToOne
    private UserEntity user;
}



