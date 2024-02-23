package com.codeblox.springsecurityfullstack.service.user;

import com.codeblox.springsecurityfullstack.configuration.security.JwtService;
import com.codeblox.springsecurityfullstack.entity.user.PasswordModification;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import com.codeblox.springsecurityfullstack.repository.user.PasswordModificationRepository;
import com.codeblox.springsecurityfullstack.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class PasswordModificationServiceImpl implements IPasswordModificationService{

    private PasswordModificationRepository passwordModificationRepository;

    private NotificationService notificationService;

    private UserServiceImpl userService;

    private JwtService jwtService;

    @Override
    public void getPasswordModificationCode(Map<String, String> email) {
        String userEmail = null;
        UserEntity currentUser = null;
        if (email.get("email")== null){
            currentUser = jwtService.getCurrentUser();
        }else{
            userEmail = email.get("email");
            currentUser = userService.findByUsername(userEmail);
        }
        PasswordModification passwordModification = new PasswordModification(generateRandomCode(), Instant.now().plus(5, ChronoUnit.MINUTES), currentUser);
        passwordModificationRepository.save(passwordModification);

        String subject = """
                CHAT-APP - Change your password
                """;
        String message = """
                Do not share this code,
                Here is your password modification code:
                """ + passwordModification.getCode();
        notificationService.sendMail(message, subject, currentUser.getEmail());
    }



    public void validateCode(Map<String, String> code){
        String codeToValidate = code.get("code");
        PasswordModification passwordModification = passwordModificationRepository.findByCode(codeToValidate).orElseThrow(() -> new RuntimeException("The provided code is not valid"));
        if (Instant.now().isAfter(passwordModification.getExpirationTime())){
            throw new RuntimeException("The code has expired");
        }
    }


    public String generateRandomCode(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

}
