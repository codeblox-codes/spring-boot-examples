package com.codeblox.springsecurityfullstack.entity.user;

import com.codeblox.springsecurityfullstack.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRole extends BaseEntity {

    private String name;
}
