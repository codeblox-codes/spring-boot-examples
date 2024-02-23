package com.codeblox.springsecurityfullstack.entity.user;

import com.codeblox.springsecurityfullstack.entity.BaseEntity;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordModification extends BaseEntity {

    private String code;

    private Instant expirationTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserEntity user;
}
