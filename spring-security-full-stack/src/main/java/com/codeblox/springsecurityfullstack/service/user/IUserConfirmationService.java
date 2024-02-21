package com.codeblox.springsecurityfullstack.service.user;

import com.codeblox.springsecurityfullstack.entity.user.UserConfirmation;
import com.codeblox.springsecurityfullstack.entity.user.UserEntity;

public interface IUserConfirmationService {
    UserConfirmation saveAndGetValidationCode(UserEntity user);

    UserConfirmation findUserConfirmationByValidationCode(String validationCode);
}
