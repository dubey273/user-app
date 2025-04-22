package com.cafe.service.users.handler.rest;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/user")
    public String get(){
        meterRegistry.counter("custom_endpoint_hits_total").increment();
        log.info("New get request");
        return "demo";
    }
}
