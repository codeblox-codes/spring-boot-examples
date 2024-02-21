package com.codeblox.springsecurityfullstack.entity.user;

import com.codeblox.springsecurityfullstack.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConfirmation extends BaseEntity {

    private String validationCode;


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserEntity user;


    private Instant creationMoment;


    private Instant expirationMoment;

}
