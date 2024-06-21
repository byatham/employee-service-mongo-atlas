package com.anu.mongo.config;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableAsync
public class DataServiceThroughExecutors {

	private EmployeeRepository employeeRepository;
	private ObjectMapper objectMapper;
	private ExecutorService executor;

	@Autowired
	public DataServiceThroughExecutors(EmployeeRepository employeeRepository, ObjectMapper objectMapper,ExecutorService executor) {
		this.employeeRepository = employeeRepository;
		this.objectMapper = objectMapper;
		this.executor=executor;

	}

	@Value("classpath:employees.json")
	private Resource resource;

	// Method to save items using ExecutorService and CompletableFuture
	@PostConstruct
	@Async
	public CompletableFuture<Void> saveItemsAsync() {
		//ExecutorService executor = Executors.newFixedThreadPool(5); // Example: 5 threads

		Long startTime = System.currentTimeMillis();
		try (InputStream inputStream = resource.getInputStream()) {
			List<EmployeeModel> employeeData = objectMapper.readValue(inputStream,
					new TypeReference<List<EmployeeModel>>() {
					});

			List<CompletableFuture<Void>> futures = employeeData.stream()
					.map(item -> CompletableFuture.runAsync(() -> employeeRepository.save(item), executor))
					.collect(Collectors.toList());

			CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
			log.info("data processed successfully");
			Long endTime = System.currentTimeMillis();
			log.info("Time taken to complete data to save in db :: " + (endTime - startTime));
			// Thread.sleep(10000);// too see delay and data 

			return allFutures.whenComplete((result, throwable) -> {
				executor.shutdown(); // Shutdown the executor after completing all tasks

			});

		} catch (Exception e) {
			log.info("exceptions occured while processing data ");
		}
		return null;

	}
}
