package com.codeblox.taskmanagerbackend.service.user;

import com.codeblox.taskmanagerbackend.entity.person.User;
import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;
import com.codeblox.taskmanagerbackend.repository.user.UserConfirmationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

import static java.time.temporal.ChronoUnit.*;

@Service
@AllArgsConstructor
public class UserConfirmationServiceImpl implements IUserConfirmationService{

    private UserConfirmationRepository userConfirmationRepository;


    public UserConfirmation saveUserValidationCode(User user){
        Random random = new Random();
        Integer randomInteger = random.nextInt(999999);
        String validationCode = String.format("%06d", randomInteger);
        UserConfirmation userConfirmation = new UserConfirmation(validationCode, user, Instant.now(), Instant.now().plus(10, MINUTES));
        return userConfirmationRepository.save(userConfirmation);
    }

    @Override
    public UserConfirmation findUserConfirmationByValidationCode(Map<String, String> validationCode) throws Exception {
        return userConfirmationRepository.findByValidationCode(validationCode.get("validationCode")).orElseThrow(()->new Exception("The provided code is not correct"));
    }
}
