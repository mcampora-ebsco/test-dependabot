package com.ebsco.training.refarch.dependabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class Application {

    public static void main(String[] args) {
        log.info("Starting up dependabot application...");
        SpringApplication.run(Application.class, args);
    }
}
