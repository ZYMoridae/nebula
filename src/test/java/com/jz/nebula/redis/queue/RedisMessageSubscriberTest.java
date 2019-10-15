package com.jz.nebula.redis.queue;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.test.context.junit4.SpringRunner;

import com.jz.nebula.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisMessageSubscriberTest {

    @Test
    public void onMessageTest() {
        RedisMessageSubscriber subscriber = new RedisMessageSubscriber();
        String testMessage = "test";
        Message message = new DefaultMessage(testMessage.getBytes(), testMessage.getBytes());

        subscriber.onMessage(message, testMessage.getBytes());
        assertEquals(1, RedisMessageSubscriber.messageList.size());
    }

}
