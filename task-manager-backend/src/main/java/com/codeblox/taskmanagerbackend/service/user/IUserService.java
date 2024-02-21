package com.codeblox.taskmanagerbackend.service.user;

import com.codeblox.taskmanagerbackend.entity.person.AuthenticationDTO;
import com.codeblox.taskmanagerbackend.entity.person.User;
import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;

import java.util.Map;

public interface IUserService {
    void register(User user);

    void activateAccount(Map<String, String> validationCode) throws Exception;

    void getNewAccountValidationCode(Map<String, String> email);

    Map<String, String> signIn(AuthenticationDTO authenticationDTO);
}
