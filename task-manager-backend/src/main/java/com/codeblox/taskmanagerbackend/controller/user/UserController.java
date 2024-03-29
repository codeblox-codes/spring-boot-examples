package com.codeblox.taskmanagerbackend.controller.user;

import com.codeblox.taskmanagerbackend.entity.person.AuthenticationDTO;
import com.codeblox.taskmanagerbackend.entity.person.User;
import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;
import com.codeblox.taskmanagerbackend.service.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("users")
public class UserController {

    private UserServiceImpl userService;
    private AuthenticationManager authenticationManager;


    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody User user){
        userService.register(user);
        return new ResponseEntity<>(OK);
    }


    @PostMapping("/activate-account")
    public ResponseEntity<Void>activateAccount(@RequestBody Map<String, String> validationCode) throws Exception {
        userService.activateAccount(validationCode);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/new-activation-code")
    public ResponseEntity<Void>getNewAccountValidationCode(@RequestBody Map<String, String> email) throws Exception {
        userService.getNewAccountValidationCode(email);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Map<String, String>>signIn(@RequestBody AuthenticationDTO authenticationDTO) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password())
        );
        log.info("Result {}", authenticate.isAuthenticated());
        return new ResponseEntity<>(OK);
    }
}
