package com.codeblox.taskmanagerbackend.service.user;

import com.codeblox.taskmanagerbackend.entity.person.User;
import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;
import com.codeblox.taskmanagerbackend.repository.user.UserRepository;
import com.codeblox.taskmanagerbackend.service.notification.NotificationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService{

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private UserConfirmationServiceImpl userConfirmationService;

    private NotificationServiceImpl notificationService;

    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        UserConfirmation userConfirmation = userConfirmationService.saveUserValidationCode(user);
        String subject = """
                TASK-MANAGER - Validate your account
                """;
        String message = """
                Welcome To TaskManager,
                Here is your account activation code:
                """ + userConfirmation.getValidationCode();
        notificationService.sendMail(message, subject, user.getEmail());
    }

    @Override
    public void activateAccount(Map<String, String> validationCode) throws Exception {
        UserConfirmation userConfirmationByValidationCode = userConfirmationService.findUserConfirmationByValidationCode(validationCode);
        if (Instant.now().isAfter(userConfirmationByValidationCode.getExpirationMoment())){
            throw new RuntimeException("The provided code has expired");
        }
        User user = userRepository.findById(userConfirmationByValidationCode.getUser().getId()).orElseThrow(() -> new RuntimeException("User could not be found"));
        user.setAccountStatus(true);
        userRepository.save(user);
    }

    @Override
    public void getNewAccountValidationCode(Map<String, String> email) {
        User user = userRepository.findByEmail(email.get("email")).orElseThrow(() -> new RuntimeException("User with the provided email couldn't be found"));
        UserConfirmation userConfirmation = userConfirmationService.saveUserValidationCode(user);
        String subject = """
                TASK-MANAGER - Validate your account
                """;
        String message = """
                Welcome To TaskManager,
                Here is your account activation code:
                """ + userConfirmation.getValidationCode();
        notificationService.sendMail(message, subject, user.getEmail());
    }

}
