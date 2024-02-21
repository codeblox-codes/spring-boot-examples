package com.codeblox.springsecurityfullstack.controller.security;

import com.codeblox.springsecurityfullstack.configuration.security.JwtService;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.RefreshTokenRequestDTO;
import com.codeblox.springsecurityfullstack.configuration.security.jwt_request_dtos.JwtResponseDTO;
import com.codeblox.springsecurityfullstack.entity.security.RefreshToken;
import com.codeblox.springsecurityfullstack.service.secutity.RefreshTokenServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class RefreshTokenController {

    private RefreshTokenServiceImpl refreshTokenService;

    private JwtService jwtService;


    @PostMapping("/refresh-token")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Error !!"));
    }
}
