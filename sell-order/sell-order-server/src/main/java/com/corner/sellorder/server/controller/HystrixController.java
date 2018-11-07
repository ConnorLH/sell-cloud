package com.corner.sellorder.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //@HystrixCommand(fallbackMethod = "fallback")
    // 超时配置
    //@HystrixCommand(commandProperties = {@HystrixProperty(name = "default_executionTimeoutInMilliseconds", value = "3000")})
    // 熔断配置
    @HystrixCommand(commandProperties = {@HystrixProperty(name = "circuitBreakerEnabled", value = "true"),@HystrixProperty(name = "circuitBreakerRequestVolumeThreshold", value = "10"),@HystrixProperty(name = "circuitBreakerSleepWindowInMilliseconds", value = "10000"),@HystrixProperty(name = "circuitBreakerErrorThresholdPercentage", value = "60")})
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://127.0.0.1:8081/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
        return result;
    }

    private String fallback() {
        return "太拥挤了，请稍后再试~~~";
    }
    private String defaultFallback() {
        return "默认提示，太拥挤了，请稍后再试~~~";
    }
}
