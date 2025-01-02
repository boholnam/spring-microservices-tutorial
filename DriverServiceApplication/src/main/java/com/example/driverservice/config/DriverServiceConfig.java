package com.example.driverservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverServiceConfig {

    @Bean
    public String driverServiceBean() {
        return "Driver Service Configuration Bean Loaded";
    }
}