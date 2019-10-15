package com.jz.nebula.redis;

import com.jz.nebula.redis.queue.MessagePublisher;
import com.jz.nebula.redis.queue.RedisMessagePublisher;
import com.jz.nebula.redis.queue.RedisMessageSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

import java.util.Arrays;
import java.util.List;

//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@ComponentScan("com.jz.nebula")
@EnableRedisRepositories(basePackages = "com.jz.nebula.repository")
@PropertySource("classpath:application.yml")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    //    TODO: Find out why this config loading caused the testing failed
//    @Value("${spring.redis.cluster.nodes}")
    private String redisClusterNodes;

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = null;
        RedisConfiguration redisConfiguration;

        if (redisClusterNodes != null && !redisClusterNodes.equals("")) {
            String[] nodesArray = redisClusterNodes.split(",");
            List<String> ndoesList = Arrays.asList(nodesArray);
            redisConfiguration = new RedisClusterConfiguration(ndoesList);
        } else {
            redisConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
            if (redisConfiguration instanceof RedisStandaloneConfiguration && !redisPassword.equals("")) {
                ((RedisStandaloneConfiguration) redisConfiguration).setPassword(redisPassword);
            }
        }

//		redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        if (redisConfiguration instanceof RedisClusterConfiguration) {
            connectionFactory = new JedisConnectionFactory((RedisClusterConfiguration) redisConfiguration);
        } else if (redisConfiguration instanceof RedisStandaloneConfiguration) {
            connectionFactory = new JedisConnectionFactory((RedisStandaloneConfiguration) redisConfiguration);
        }

        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;
    }

    @Bean
    MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue");
    }
}