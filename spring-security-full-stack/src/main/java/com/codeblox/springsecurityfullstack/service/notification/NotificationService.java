package com.codeblox.springsecurityfullstack.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {


    private JavaMailSender javaMailSender;

    public void sendMail(String content, String subject, String to){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("chatapp@codeblox.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

}
