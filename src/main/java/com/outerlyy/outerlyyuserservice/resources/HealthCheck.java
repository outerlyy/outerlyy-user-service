package com.outerlyy.outerlyyuserservice.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheck {

   Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @GetMapping("/user-service/health")
    public String healthCheck(){

        logger.info("HealthCheck ...");
        return "200";
    }
}