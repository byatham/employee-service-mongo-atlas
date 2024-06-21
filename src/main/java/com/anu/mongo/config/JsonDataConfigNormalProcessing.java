package com.anu.mongo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

//@Component  // enable this to save data through normal processor with single thread
public class JsonDataConfigNormalProcessing {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

   // @Value("{classpath:employees.json}")
    @Value("classpath:employees.json")
    private Resource resource; // Autowire your resource, assuming it's properly configured

    @PostConstruct
    public void jsonDbInit() {
    	 Long startTime=System.currentTimeMillis();
        System.out.println("jsonDbInit data processing");
        try (InputStream inputStream = resource.getInputStream()) {
            List<EmployeeModel> employeeData = objectMapper.readValue(inputStream, new TypeReference<List<EmployeeModel>>() {});

            // Save employees asynchronously using saveAll
            employeeRepository.saveAll(employeeData);
            System.out.println("jsonDbInit data processed");
            Long endTime=System.currentTimeMillis();
            System.out.println("Time taken to complete data to save in db :: "+(endTime-startTime));
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
