package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.listener",
        "com.example.calculator",
        "com.example.common",
"com.example.config"})
public class ModuleMain {
    public static void main(String[] args) {
        SpringApplication.run(ModuleMain.class, args);
    }
}



