package com.codeblox.taskmanagerbackend.service.notification;

public interface INotificationService {
    void sendMail(String message, String subject, String to);
}
