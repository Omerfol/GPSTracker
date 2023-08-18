package com.GPSTracker.gpstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigForEmail.class)
public class GpstrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpstrackerApplication.class, args);
    }


}

