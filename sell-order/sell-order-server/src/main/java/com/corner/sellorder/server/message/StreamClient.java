package com.corner.sellorder.server.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {

    String INPUT = "myMessageInput";
    String OUTPUT="myMessageOutput";

    /**
     * 定义接收信道
     * @return
     */
    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    /**
     * 定义发送信道
     * @return
     */
    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}
