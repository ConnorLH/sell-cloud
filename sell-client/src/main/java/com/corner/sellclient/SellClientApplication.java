package com.corner.sellclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//其他注册中心的话使用这个注解启动注册
//@EnableDiscoveryClient
public class SellClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellClientApplication.class, args);
    }
}
