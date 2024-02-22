package com.codeblox.springsecurityfullstack.controller.user;

import com.codeblox.springsecurityfullstack.configuration.security.JwtService;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.RequestDTO;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.JwtResponseDTO;
import com.codeblox.springsecurityfullstack.entity.dtos.ProtectedUserContentDTO;
import com.codeblox.springsecurityfullstack.entity.security.RefreshToken;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;
import com.codeblox.springsecurityfullstack.service.secutity.RefreshTokenServiceImpl;
import com.codeblox.springsecurityfullstack.service.user.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController{

    private UserServiceImpl userService;

    private JwtService jwtService;

    private RefreshTokenServiceImpl refreshTokenService;

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
    public ResponseEntity<JwtResponseDTO> login(@RequestBody RequestDTO requestDTO){
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(requestDTO.getUsername());
            JwtResponseDTO response = JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(requestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();
            return new ResponseEntity<>(response, OK);
        } else {
            System.out.println("SecurityContextHolder.getContext().getAuthentication().getPrincipal()");
            throw new UsernameNotFoundException("Invalid request..!!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        jwtService.logout();
        return new ResponseEntity<>(OK);
    }


    @GetMapping("/protected-content-for-users")
    @PreAuthorize("hasAuthority('USER')")
    public ProtectedUserContentDTO getContent(){
        return new ProtectedUserContentDTO("Hello, this is a content for users only");
    }
}
