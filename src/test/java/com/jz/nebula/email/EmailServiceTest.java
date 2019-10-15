package com.jz.nebula.email;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.jz.nebula.Application;
import com.jz.nebula.mail.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void sendSimpleMessageTest() {
        String from = "testFrom";
        String to = "testTo";
        String subject = "testSubject";
        String text = "testText";
        String[] expected = new String[]{to};
        Mockito.doAnswer((Answer<?>) invocation -> {
            SimpleMailMessage message = (SimpleMailMessage) invocation.getArgument(0);

            assertEquals(from, message.getFrom());
            assertArrayEquals(expected, message.getTo());
            assertEquals(subject, message.getSubject());
            assertEquals(text, message.getText());
            return null;
        }).when(emailSender).send(Mockito.any(SimpleMailMessage.class));

        emailService.sendSimpleMessage(from, to, subject, text);
    }

    @Test
    public void sendSimpleMessageUsingTemplateTest() {
        String from = "testFrom";
        String to = "testTo";
        String subject = "testSubject";
        String text = "testText";
        String[] expected = new String[]{to};
        SimpleMailMessage template = new SimpleMailMessage();
        template.setText(text);

        Mockito.doAnswer((Answer<?>) invocation -> {
            SimpleMailMessage message = (SimpleMailMessage) invocation.getArgument(0);

            assertEquals(from, message.getFrom());
            assertArrayEquals(expected, message.getTo());
            assertEquals(subject, message.getSubject());
            assertEquals(text, message.getText());
            return null;
        }).when(emailSender).send(Mockito.any(SimpleMailMessage.class));

        emailService.sendSimpleMessageUsingTemplate(from, to, subject, template);
    }

//	@Test
//	public void sendMessageWithAttachmentTest() {
//		String from = "testFrom";
//		String to = "testTo";
//		String subject = "testSubject";
//		String text = "testText";
//		SimpleMailMessage template = new SimpleMailMessage();
//		template.setText(text);
//
//		MimeMessage parameterMimeMessage = null;
//		try {
//			parameterMimeMessage = new MimeMessage(parameterMimeMessage);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Mockito.when(emailSender.createMimeMessage()).thenReturn(parameterMimeMessage);
//
//		Mockito.doAnswer((Answer<?>) invocation -> {
//			MimeMessage message = (MimeMessage) invocation.getArgument(0);
//			assertEquals(from, message.getFrom());
//			assertEquals(subject, message.getSubject());
//			return null;
//		}).when(emailSender).send(Mockito.any(SimpleMailMessage.class));
//
//		emailService.sendMessageWithAttachment(from, to, subject, text, "/test/test.pdf");
//	}

}
