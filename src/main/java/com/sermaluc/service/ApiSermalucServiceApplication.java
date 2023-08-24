package com.sermaluc.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ApiSermalucServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSermalucServiceApplication.class, args);
	}

}
