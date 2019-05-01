package com.jz.nebula.amqp;

import com.jz.nebula.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    @Autowired
    public EmailService emailService;

    @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue1Name})
    public void receiveMessageFromFanout1(String message) {
        logger.info("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue2Name})
    public void receiveMessageFromFanout2(String message) {
        logger.info("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.topicQueuePdf})
    public void receiveMessageFromTopicPdf(String message) {
        logger.info("Received " + SpringAmqpConfig.topicQueuePdf + " message: " + message);
        emailService.sendMessageWithAttachment("support@nebula.com.au", "ashzhouyue@gmail.com", "Nebula Report",
                "Hello, this is from nebula system", "/home/yue/Desktop/george.jpg");
    }

    @RabbitListener(queues = {SpringAmqpConfig.topicQueueInvoice})
    public void receiveMessageFromTopicInvoice(String message) {
        logger.info("Received " + SpringAmqpConfig.topicQueueInvoice + " message: " + message);
    }
}
