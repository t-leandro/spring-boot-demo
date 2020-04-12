package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

// TODO : logs - check it
// TODO : check request id in http response
// TODO : clean code

@SpringBootApplication(scanBasePackages = {"com.example.config", "com.example.app",
        "com.example.common", "com.example.rabbitmq", "com.example.senders"})
@EnableAsync
public class HomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeApplication.class, args);
    }
}
