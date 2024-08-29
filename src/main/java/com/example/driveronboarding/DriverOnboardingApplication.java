package com.example.driveronboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DriverOnboardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverOnboardingApplication.class, args);
    }

}
