package com.arpita.nexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexusApplication {

	public static void main(String[] args) {

		System.setProperty("user.timezone", "Asia/Kolkata");
		SpringApplication.run(NexusApplication.class, args);
	}

}
