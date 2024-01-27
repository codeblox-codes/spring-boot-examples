package com.codeblox.taskmanagerbackend.service.user;

import com.codeblox.taskmanagerbackend.entity.person.UserConfirmation;

import java.util.Map;

public interface IUserConfirmationService {
    UserConfirmation findUserConfirmationByValidationCode(Map<String, String> validationCode) throws Exception;
}
