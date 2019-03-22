package com.jz.nebula.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jz.nebula.mail.EmailService;

@Component
public class MessageConsumer {
  @Autowired
  public EmailService emailService;
	
  private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

  @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue1Name})
  public void receiveMessageFromFanout1(String message) {
      logger.info("Received fanout 1 message: " + message);
  }

  @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue2Name})
  public void receiveMessageFromFanout2(String message) {
      logger.info("Received fanout 2 message: " + message);
  }

  @RabbitListener(queues = {SpringAmqpConfig.topicQueue1Name})
  public void receiveMessageFromTopic1(String message) {
      logger.info("Received " + SpringAmqpConfig.topicQueue1Name + " message: " + message);
      emailService.sendMessageWithAttachment("support@nebula.com.au", "ashzhouyue@gmail.com", "Nebula Report", "Hello, this is from nebula system", "/home/yue/Desktop/george.jpg");
  }

  @RabbitListener(queues = {SpringAmqpConfig.topicQueue2Name})
  public void receiveMessageFromTopic2(String message) {
      logger.info("Received " + SpringAmqpConfig.topicQueue2Name + " message: " + message);
  }
}
