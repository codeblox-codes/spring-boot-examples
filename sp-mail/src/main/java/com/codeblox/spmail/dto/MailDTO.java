package com.codeblox.spmail.dto;

import lombok.Getter;

public record MailDTO(String email, String message, String subject) {
}
