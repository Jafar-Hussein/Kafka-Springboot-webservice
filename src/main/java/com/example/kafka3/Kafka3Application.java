package com.example.kafka3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class Kafka3Application {

	public static void main(String[] args) {
		SpringApplication.run(Kafka3Application.class, args);
	}

}
