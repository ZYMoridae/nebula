package com.jz.nebula.mail;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

//import java.io.File;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

@Component
public class EmailService implements EmailServiceInterface {

	@Autowired
	public JavaMailSender emailSender;

	public void sendSimpleMessage(String from, String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void sendSimpleMessageUsingTemplate(String from, String to, String subject, SimpleMailMessage template,
			String... templateArgs) {
		String text = template.getText();
		sendSimpleMessage(from, to, subject, text);
	}

	@Override
	public void sendMessageWithAttachment(String from, String to, String subject, String text,
			String pathToAttachment) {
//		try {
//			MimeMessage message = emailSender.createMimeMessage();
//			// pass 'true' to the constructor to create a multipart message
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//			helper.setFrom(from);
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(text);
//
//			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//			helper.addAttachment("Invoice", file);
//
//			emailSender.send(message);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
	}
}
