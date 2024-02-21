package com.codeblox.springsecurityfullstack.repository.user;

import com.codeblox.springsecurityfullstack.entity.user.UserConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfirmationRepository extends JpaRepository<UserConfirmation, Long> {
    Optional<UserConfirmation> findUserConfirmationByValidationCode(String validationCode);
}
