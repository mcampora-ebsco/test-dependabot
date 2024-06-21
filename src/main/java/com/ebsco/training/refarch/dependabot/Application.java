package com.ebsco.training.refarch.dependabot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableScheduling
@Log4j2
public class Application {
    // secrets hardcoded in code to test GH secrets detection
    String americanExpress = "378282246310005";
    String visa = "4111111111111111";
    String password = "password";
    String token = "AKCp8ogq20EEZH5Gs7f83RSLPTB6npMoau2NZEKhKh5uNid4CNdBPaQe4RkWymobAkkcCcd9KB";
    String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjp7ImVtYWlsIjoicnNhX2xvcmRAanVpY2Utc2gub3AifSwiaWF0IjoxNTgyMjIxNTc1fQ.ycFwtqh4ht4Pq9K5rhiPPY256F9YCTIecd4FHFuSEAg";

    public static void main(String[] args) {
        log.info("Starting up dependabot application...");
        SpringApplication.run(Application.class, args);
    }
}
