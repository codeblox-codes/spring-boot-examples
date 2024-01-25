package com.codeblox.spmail.service;

import com.codeblox.spmail.dto.MailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private MailServiceImpl mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testIsEmailValid_ValidEmail_ShouldReturnTrue() {
        assertTrue(mailService.isEmailValid("tuto@codblox.com"));
    }

    @Test
    void testIsEmailValid_InvalidEmail_ShouldReturnFalse(){
        assertFalse(mailService.isEmailValid("invalid-email"));
    }

    @Test
    void testSendMail_ValidMailDTO_ShouldSendEmail() {
        MailDTO validMailDTO = new MailDTO("test@example.com", "Test Subject", "Test Message");

        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> mailService.sendMail(validMailDTO));

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendMail_InvalidMailDTO_ShouldThrowException() {
        MailDTO invalidMailDTO = new MailDTO("invalid-email", "Test Subject", "Test Message");

        assertThrows(RuntimeException.class, () -> mailService.sendMail(invalidMailDTO));

        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}