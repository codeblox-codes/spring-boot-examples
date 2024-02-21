package com.codeblox.springsecurityfullstack.service.user;


import com.codeblox.springsecurityfullstack.entity.user.UserConfirmation;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import com.codeblox.springsecurityfullstack.entity.user.UserRole;
import com.codeblox.springsecurityfullstack.repository.user.UserRepository;
import com.codeblox.springsecurityfullstack.repository.user.UserRoleRepository;
import com.codeblox.springsecurityfullstack.service.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Service
@NoArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private IUserConfirmationServiceService userConfirmationServiceImpl;

    private NotificationService notificationService;

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, IUserConfirmationServiceService userConfirmationServiceImpl, NotificationService notificationService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userConfirmationServiceImpl = userConfirmationServiceImpl;
        this.notificationService = notificationService;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User with the provided username doesn't exist"));
    }



    @Override
    public void register(UserEntity user) {
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getUsername());
        UserRole savedUserRole = userRoleRepository.save(new UserRole("USER"));
        user.setRoles(Set.of(savedUserRole));
        userRepository.save(user);
        UserConfirmation userConfirmation = userConfirmationServiceImpl.saveAndGetValidationCode(user);
        String subject = """
                CHAT-APP - Validate your account
                """;
        String message = """
                Welcome To ChatApp,
                Here is your account activation code:
                """ + userConfirmation.getValidationCode();

        notificationService.sendMail(message, subject, user.getEmail());
    }


    @Override
    public void activateAccount(Map<String, String> validationCode) {
        UserConfirmation userConfirmation = userConfirmationServiceImpl.findUserConfirmationByValidationCode(validationCode.get("validationCode"));
        if(Instant.now().isAfter(userConfirmation.getExpirationMoment())){
            throw new RuntimeException("The code has expired");
        }
        UserEntity user = userRepository.findByUsername(userConfirmation.getUser().getEmail()).orElseThrow(() -> new RuntimeException("No user found"));
        user.setIsActive(true);
        userRepository.save(user);
    }
}
