package com.anu.mongo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

//@Component
public class DataInitializer {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:employees.json")
    private Resource resource; 

    @Autowired
    @Qualifier("taskExecutor")
    private ExecutorService executorService;

    @PostConstruct
    @Transactional
    public CompletableFuture<Void> jsonDbInit() {
        System.out.println("jsonDbInit data processing");

        try (InputStream inputStream = resource.getInputStream()) {
            List<EmployeeModel> employeeData = objectMapper.readValue(inputStream, new TypeReference<List<EmployeeModel>>() {});
            
            for (EmployeeModel employee : employeeData) {
                Runnable worker = new DatabaseWorker(employee);
                 executorService.execute(worker);
                 
            }
            System.out.println("data has been processed successfully ");

        } catch (IOException e) {
            e.printStackTrace();
            return CompletableFuture.failedFuture(e); // Handle exception asynchronously
        }
        finally {
        	executorService.shutdown();
		}
        return null;
    }
    
    
    static class DatabaseWorker implements Runnable {
        private final EmployeeModel employee;

        DatabaseWorker(EmployeeModel employeeData) {
            this.employee = employeeData;
        }

		@Override
		public void run() {
			
			
		}
}
}
