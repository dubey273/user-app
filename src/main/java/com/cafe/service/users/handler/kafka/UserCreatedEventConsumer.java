package com.cafe.service.users.handler.kafka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Component
//@Slf4j
public class UserCreatedEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserCreatedEventConsumer.class);

   // @KafkaListener(topics = "mytopic",groupId = "mytopic.groupid")
    public void handle(String event, Acknowledgment acknowledgment){
        try{
            System.out.println(event);
            log.info("Inside the handle event");
        }catch (Exception e){
            log.error("exception e",e);
        } finally {
          acknowledgment.acknowledge();
        }

    }
}
