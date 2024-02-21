package com.codeblox.springsecurityfullstack.service.user;

import com.codeblox.springsecurityfullstack.entity.user.UserEntity;

import java.util.Map;

public interface IUserService {
    void register(UserEntity user);

    void activateAccount(Map<String, String> validationCode);
}
