package com.corner.sellorder.server.controller;

import com.corner.sellorder.server.dto.OrderDTO;
import com.corner.sellorder.server.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void sendString(){
        streamClient.output().send(MessageBuilder.withPayload("只是测试是事实").build());
    }

    @GetMapping("/sendObejctMessage")
    public void sendObject(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("khflaflajfla");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
