package com.corner.sellorder.server.message;

import com.corner.sellorder.server.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(StreamClient.class)
public class StreamReceiver {

    /**
     * 监听发送信道，这里就可以监听到接收后发送的回执消息
     * 注意这里是字节数组
     * ??每次重启后发送的第一个消息会直接被这个监听到，但是不会发到MQ（当然接收信道也没有），
     * 从第二个消息开始会先被接收信道结束，其返回回执消息后才被这里监听到（第二个开始发送时不再监听）?
     * @param message
     */
    @StreamListener(StreamClient.OUTPUT)
    public void processOutput(OrderDTO message){
        log.info("StreamReceiverOutput:{}",message);
    }

    /**
     * 监听接收信道
     * 注意这里是字节数组
     * @param message
     * @return
     */
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.OUTPUT)
    public Object processInput(OrderDTO message){
        //log.info("StreamReceiverInput:{}",new String((byte[]) message));
        log.info("StreamReceiverInput:{}",message);
        message.setBuyerAddress("已经收货了");
        return message;
    }
}
