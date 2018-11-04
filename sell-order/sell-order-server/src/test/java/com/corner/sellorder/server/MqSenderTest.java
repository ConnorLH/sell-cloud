package com.corner.sellorder.server;

import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqSenderTest extends SellOrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMessage(){
        amqpTemplate.convertAndSend("myQueue","now you see " + new Date().toString());
    }
}
