package com.codeblox.springsecurityfullstack.controller.user;

import com.codeblox.springsecurityfullstack.configuration.security.JwtService;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.RequestDTO;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.ResponseDTO;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import com.codeblox.springsecurityfullstack.service.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController{

    private UserServiceImpl userService;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;


    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody UserEntity user){
        userService.register(user);
        return new ResponseEntity<>(OK);
    }


    @PostMapping("/activate-account")
    public ResponseEntity<UserEntity> activateAccount(@RequestBody Map<String, String> validationCode){
        userService.activateAccount(validationCode);
        return new ResponseEntity<>(OK);
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody RequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new UsernameNotFoundException("Invalid request..!!");
        } else {
            ResponseDTO response = ResponseDTO.builder()
                    .accessToken(jwtService.generateToken(requestDTO.getUsername()))
                    .build();
            return new ResponseEntity<>(response, OK);
        }
    }
}
