package com.anu.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableAsync
@Slf4j
@EnableCaching
public class EmployeeServiceMongoAtlasApplication {
	public static String value="employee-service-mongo-atlas";

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceMongoAtlasApplication.class, args);
		log.trace("doStuff needed more information - {}", value);
        log.debug("doStuff needed to debug - {}", value);
        log.info("doStuff took input - {}", value);
        log.warn("doStuff needed to warn - {}", value);
        log.error("doStuff encountered an error with value - {}", value);
        log.info("doStuff took input mg-101 - {}", value);

	}
	
	@Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "employee-service-mongo-atlas");
    }

}
