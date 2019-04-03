package com.jz.nebula.amqp;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("!test")
public class SpringAmqpConfig {

	public final static String fanoutQueue1Name = "nebula.fanout.queue1";
	public final static String fanoutQueue2Name = "nebula.fanout.queue2";
	public final static String fanoutExchangeName = "nebula.fanout.exchange";

	public final static String topicQueuePdf = "nebula.topic.pdf";
	public final static String topicQueueInvoice = "nebula.topic.invoice";
	public final static String topicExchangeName = "nebula.topic.exchange";
	
	@Bean
	public List<Declarable> topicBindings() {
		Queue topicQueue1 = new Queue(topicQueuePdf, false);
		Queue topicQueue2 = new Queue(topicQueueInvoice, false);

		TopicExchange topicExchange = new TopicExchange(topicExchangeName);

		return Arrays.asList(topicQueue1, topicQueue2, topicExchange,
				BindingBuilder.bind(topicQueue1).to(topicExchange).with("pdf.*"),
				BindingBuilder.bind(topicQueue2).to(topicExchange).with("invoice.*"));
	}

	@Bean
	public List<Declarable> fanoutBindings() {
		Queue fanoutQueue1 = new Queue(fanoutQueue1Name, false);
		Queue fanoutQueue2 = new Queue(fanoutQueue2Name, false);

		FanoutExchange fanoutExchange = new FanoutExchange(fanoutExchangeName);

		return Arrays.asList(fanoutQueue1, fanoutQueue2, fanoutExchange,
				BindingBuilder.bind(fanoutQueue1).to(fanoutExchange), BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
	}

	@Bean
	public SimpleRabbitListenerContainerFactory broadcastContainer(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}
}