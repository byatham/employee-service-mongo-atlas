package com.anu.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication
@EnableAsync
public class EmployeeServiceMongoAtlasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceMongoAtlasApplication.class, args);
	}
	
	@Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "employee-service-mongo-atlas");
    }

}
