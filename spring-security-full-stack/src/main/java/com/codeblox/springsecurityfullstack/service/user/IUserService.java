package com.codeblox.springsecurityfullstack.service.user;

import com.codeblox.springsecurityfullstack.entity.dtos.UserResponseDTO;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;

import java.util.Map;

public interface IUserService {
    void register(UserEntity user);

    void activateAccount(Map<String, String> validationCode);

    void modifyPassword(Map<String, String> passwordModificationDTO);

    UserResponseDTO updateUser(UserEntity user);

    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);
}
