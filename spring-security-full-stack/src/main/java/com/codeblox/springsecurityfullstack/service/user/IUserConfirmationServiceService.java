package com.codeblox.springsecurityfullstack.service.user;

import com.codeblox.springsecurityfullstack.entity.user.UserConfirmation;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import com.codeblox.springsecurityfullstack.repository.user.UserConfirmationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@AllArgsConstructor
public class IUserConfirmationServiceService implements IUserConfirmationService {

    private UserConfirmationRepository userConfirmationRepository;

    @Override
    public UserConfirmation saveAndGetValidationCode(UserEntity user) {
        Random random = new Random();
        Integer randomInteger = random.nextInt(999999);
        String validationCode = String.format("%06d", randomInteger);
        return userConfirmationRepository.save(new UserConfirmation(validationCode, user, Instant.now(), Instant.now().plus(10, ChronoUnit.MINUTES)));
    }

    @Override
    public UserConfirmation findUserConfirmationByValidationCode(String validationCode) {
        return userConfirmationRepository.findUserConfirmationByValidationCode(validationCode).orElseThrow(()->new RuntimeException("The provided code is not correct"));
    }
}
