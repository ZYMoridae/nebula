package com.jz.nebula.config.rabbitmq;

import com.jz.nebula.service.mail.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    @Autowired
    public EmailServiceImpl emailServiceImpl;

    @RabbitListener(queues = {RabbitMqConfig.fanoutQueue1Name})
    public void receiveMessageFromFanout1(String message) {
        logger.info("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = {RabbitMqConfig.fanoutQueue2Name})
    public void receiveMessageFromFanout2(String message) {
        logger.info("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = {RabbitMqConfig.topicQueuePdf})
    public void receiveMessageFromTopicPdf(String message) {
        logger.info("Received " + RabbitMqConfig.topicQueuePdf + " message: " + message);
        emailServiceImpl.sendMessageWithAttachment("support@nebula.com.au", "ashzhouyue@gmail.com", "Nebula Report",
                "Hello, this is from nebula system", "/home/yue/Desktop/george.jpg");
    }

    @RabbitListener(queues = {RabbitMqConfig.topicQueueInvoice})
    public void receiveMessageFromTopicInvoice(String message) {
        logger.info("Received " + RabbitMqConfig.topicQueueInvoice + " message: " + message);
    }
}
