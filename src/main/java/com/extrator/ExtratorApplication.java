package com.extrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.extrator")
public class ExtratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtratorApplication.class, args);
    }
}