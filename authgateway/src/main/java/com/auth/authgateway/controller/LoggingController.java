package com.auth.authgateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@Slf4j
@RestController
@RequestMapping(LoggingController.ENDPOINT)
public class LoggingController {
    public static final String ENDPOINT = "/log";

    @GetMapping
    public String log() {
        log.info("It's working, Good Job buddy !!");
        return "It's working fine!";
    }

}
