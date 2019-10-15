package com.jz.nebula.mail;

import org.springframework.mail.SimpleMailMessage;

public interface EmailServiceInterface {
    void sendSimpleMessage(String from, String to, String subject, String text);

    void sendSimpleMessageUsingTemplate(String from, String to, String subject, SimpleMailMessage template,
                                        String... templateArgs);

    void sendMessageWithAttachment(String from, String to, String subject, String text, String pathToAttachment);
}
