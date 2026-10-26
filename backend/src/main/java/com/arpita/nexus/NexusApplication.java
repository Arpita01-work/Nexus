package com.arpita.nexus;

import com.arpita.nexus.security.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class NexusApplication {

    public static void main(String[] args) {

        System.setProperty("user.timezone", "Asia/Kolkata");
        SpringApplication.run(NexusApplication.class, args);
    }
}
