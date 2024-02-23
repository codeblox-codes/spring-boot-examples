package com.codeblox.springsecurityfullstack.repository.user;

import com.codeblox.springsecurityfullstack.entity.user.PasswordModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordModificationRepository extends JpaRepository<PasswordModification, Long> {
    Optional<PasswordModification> findByCode(String code);
}
