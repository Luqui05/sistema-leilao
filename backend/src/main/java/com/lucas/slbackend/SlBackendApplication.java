package com.lucas.slbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lucas")
public class SlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlBackendApplication.class, args);
	}

}
