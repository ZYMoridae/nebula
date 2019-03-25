package com.jz.nebula.amqp;

import java.util.regex.Pattern;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
      rabbitTemplate.convertAndSend(SpringAmqpConfig.fanoutExchangeName, "", message);
      
      if(Pattern.matches("^pdf.*", message)) {
      	System.out.println("123");
      	rabbitTemplate.convertAndSend(SpringAmqpConfig.topicExchangeName, "pdf." + message, message);
      }else if(Pattern.matches("^invoice\\..*", message)){
      	rabbitTemplate.convertAndSend(SpringAmqpConfig.topicExchangeName, message, message);
      }
    }
}
