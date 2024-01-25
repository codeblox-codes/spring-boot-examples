package com.codeblox.spmail.service;

import com.codeblox.spmail.dto.MailDTO;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class MailServiceImpl implements IMealService{

    private JavaMailSender javaMailSender;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public Boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void sendMail(MailDTO mailDTO){
        if (!isEmailValid(mailDTO.email())) {
            throw new RuntimeException("The provided email is not valid");
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(mailDTO.subject());
        mailMessage.setTo(mailDTO.email());
        mailMessage.setText(mailDTO.message());
        javaMailSender.send(mailMessage);
    }
}
