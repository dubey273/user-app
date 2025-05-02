package com.cafe.service.users.handler.rest;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private MeterRegistry meterRegistry;

    @PreAuthorize("hasRole('ROLE_read')")
    @GetMapping("/user")
    public String get(){
        //meterRegistry.counter("custom_endpoint_hits_total").increment();
        log.info("New get request added");
        return "get";
    }

    @PreAuthorize("hasRole('ROLE_write')")
    @PostMapping("/user")
    public String post(@RequestBody String hello){
        return "added";
    }
}
