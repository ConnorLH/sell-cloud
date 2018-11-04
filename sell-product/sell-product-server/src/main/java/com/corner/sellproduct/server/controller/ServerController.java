package com.corner.sellproduct.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServerController {

    @GetMapping("/msg")
    public String msg() {
        log.info("this is 2 msg");
        return "this is msg 2";
    }
}
