package com.codeblox.springsecurityfullstack.service.user;

import java.util.Map;

public interface IPasswordModificationService {
    void getPasswordModificationCode(Map<String, String> passwordModificationDTO);
}
