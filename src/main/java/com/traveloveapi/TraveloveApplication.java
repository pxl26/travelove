package com.traveloveapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TraveloveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraveloveApplication.class, args);
    }

}
