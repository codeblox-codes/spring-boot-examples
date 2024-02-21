package com.codeblox.taskmanagerbackend.entity.person;

import com.codeblox.taskmanagerbackend.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuthority extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @OneToOne
    private User user;

    public UserAuthority(AuthorityType authorityType) {
        this.authorityType = authorityType;
    }
}
