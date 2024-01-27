package com.codeblox.taskmanagerbackend.service.notification;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements INotificationService {


    private JavaMailSender mailSender;

    public void sendMail(String message, String subject, String to){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setFrom("taskmanager@codeblox.com");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }}
