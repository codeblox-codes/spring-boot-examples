package com.codeblox.taskmanagerbackend.repository.user;

import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfirmationRepository extends JpaRepository<UserConfirmation, Long> {
    Optional<UserConfirmation> findByValidationCode(String validationCode);
}
