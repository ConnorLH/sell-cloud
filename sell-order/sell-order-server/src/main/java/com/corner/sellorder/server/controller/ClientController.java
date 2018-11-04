package com.corner.sellorder.server.controller;

import com.corner.sellproduct.client.ProductClient;
import com.corner.sellproduct.common.dto.DecreaseStockInput;
import com.corner.sellproduct.common.dto.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    //@Autowired
    //private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        //1、第一种方式（直接使用restTemplate，url写死）
        //RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("http://localhost:8081/msg",String.class);
        //2、第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate）
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance product = loadBalancerClient.choose("PRODUCT");
        String productServiceInfo = String.format("http://%s:%s", product.getHost(), product.getPort()) + "/msg";
        String response = restTemplate.getForObject(productServiceInfo,String.class);
        //3、第三种方式（利用@LoadBalanced，可在restTemplate里使用应用名字）
        //String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        log.info("response={}",response);
        return response;
    }

    @GetMapping("/getProductMsgByFeign")
    public String getProductMsgByFeign() {
        String response = productClient.productMsg();
        log.info("response={}",response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
        log.info("response={}",productInfoList);
        return "ok";
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock(){
        productClient.decreaseStock(Arrays.asList(new DecreaseStockInput("164103465734242707",3)));
        return "ok";
    }
}
