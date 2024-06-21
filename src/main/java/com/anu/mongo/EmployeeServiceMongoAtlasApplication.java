package com.anu.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EmployeeServiceMongoAtlasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceMongoAtlasApplication.class, args);
	}

}
