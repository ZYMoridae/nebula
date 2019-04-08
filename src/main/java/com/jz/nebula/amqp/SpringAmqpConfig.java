package com.jz.nebula.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class SpringAmqpConfig {

	public final static String fanoutQueue1Name = "nebula.fanout.queue1";
	public final static String fanoutQueue2Name = "nebula.fanout.queue2";
	public final static String fanoutExchangeName = "nebula.fanout.exchange";

	public final static String topicQueuePdf = "nebula.topic.pdf";
	public final static String topicQueueInvoice = "nebula.topic.invoice";
	public final static String topicExchangeName = "nebula.topic.exchange";

//	@Bean
//	public List<Declarable> topicBindings() {
//		Queue topicQueue1 = new Queue(topicQueuePdf, false);
//		Queue topicQueue2 = new Queue(topicQueueInvoice, false);
//
//		TopicExchange topicExchange = new TopicExchange(topicExchangeName);
//
//		return Arrays.asList(topicQueue1, topicQueue2, topicExchange,
//				BindingBuilder.bind(topicQueue1).to(topicExchange).with("pdf.*"),
//				BindingBuilder.bind(topicQueue2).to(topicExchange).with("invoice.*"));
//	}
//
//	@Bean
//	public List<Declarable> fanoutBindings() {
//		Queue fanoutQueue1 = new Queue(fanoutQueue1Name, false);
//		Queue fanoutQueue2 = new Queue(fanoutQueue2Name, false);
//
//		FanoutExchange fanoutExchange = new FanoutExchange(fanoutExchangeName);
//
//		return Arrays.asList(fanoutQueue1, fanoutQueue2, fanoutExchange,
//				BindingBuilder.bind(fanoutQueue1).to(fanoutExchange), BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
//	}

//	new AnonymousQueue.Base64UrlNamingStrategy().generateName();

	@Bean
	public Queue fq1() {
		return new Queue(fanoutQueue1Name, false);
	}

	@Bean
	public Queue fq2() {
		return new Queue(fanoutQueue2Name, false);
	}

	@Bean
	public FanoutExchange fe1() {
		return new FanoutExchange(fanoutExchangeName, false, true);
	}

	@Bean
	public Queue tq1() {
		return new Queue(topicQueuePdf, false);
	}

	@Bean
	public Queue tq2() {
		return new Queue(topicQueueInvoice, false);
	}

	@Bean
	public TopicExchange te1() {
		return new TopicExchange(topicExchangeName, false, true);
	}

	@Bean
	public Binding binding3(TopicExchange te1, Queue tq1) {
		return BindingBuilder.bind(tq1).to(te1).with("pdf.*");
	}

	@Bean
	public Binding binding4(TopicExchange te1, Queue tq2) {
		return BindingBuilder.bind(tq2).to(te1).with("invoice.*");
	}

	@Bean
	public Binding binding1(FanoutExchange fe1, Queue fq1) {
		return BindingBuilder.bind(fq1).to(fe1);
	}

	@Bean
	public Binding binding2(FanoutExchange fe1, Queue fq2) {
		return BindingBuilder.bind(fq2).to(fe1);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		return new RabbitTemplate(connectionFactory());
	}
}