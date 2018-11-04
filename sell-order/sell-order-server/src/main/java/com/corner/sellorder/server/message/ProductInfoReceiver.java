package com.corner.sellorder.server.message;

import com.corner.sellorder.server.utils.JsonUtil;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.ListType;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        List<ProductInfoOutput> productInfoOutputs = JsonUtil.toObject(message, new TypeReference<List<ProductInfoOutput>>() {});
        log.info("从队列【{}】接收消息:{}","productInfo",productInfoOutputs);
        // 保存到redis中
        productInfoOutputs.forEach(item -> {
            redisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,item.getProductId()),String.valueOf(item.getProductStock()));
        });
    }
}
