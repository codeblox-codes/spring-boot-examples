package com.codeblox.spmail.service;

import com.codeblox.spmail.dto.MailDTO;

public interface IMealService {
    Boolean isEmailValid(String email);

    void sendMail(MailDTO mailDTO);
}
