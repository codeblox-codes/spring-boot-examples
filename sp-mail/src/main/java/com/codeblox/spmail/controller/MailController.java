package com.codeblox.spmail.controller;

import com.codeblox.spmail.dto.MailDTO;
import com.codeblox.spmail.service.MailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("send-mail")
public class MailController {

    private MailServiceImpl mailService;

    @PostMapping
    public ResponseEntity<Void>sendMail(@RequestBody MailDTO mailDTO){
        mailService.sendMail(mailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
