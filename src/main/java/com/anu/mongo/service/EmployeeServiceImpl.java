package com.anu.mongo.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeRepository employeeRepository;
	private ExecutorService executor;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository,ExecutorService executor) {
		this.employeeRepository=employeeRepository;
		this.executor=executor;
	}

	@Override
	public EmployeeModel saveNewEmployee(EmployeeModel employeeModel) {
		
		return employeeRepository.save(employeeModel);
	}

	@Override
	public EmployeeModel findSavedEmployeeById(Integer empId) {
		return employeeRepository.findById(empId).get();
	}

	@Override
	public List<EmployeeModel> findAllEmployees() {
		//return  CompletableFuture.supplyAsync(() -> employeeRepository.findAll(), executor);
		 
		 return employeeRepository.findAll();
	}
	

	@Override
	public EmployeeModel updateExistingEmployee(EmployeeModel updateModel) {
		
		return employeeRepository.save(updateModel);
	}

	@Override
	public String deleteEmployeeById(String empId) {
		EmployeeModel employeeModel = employeeRepository.findById(empId).get();
		if(ObjectUtils.isEmpty(employeeModel))
		{
			 employeeRepository.delete(employeeModel);
			 return "your data has been deleted in db "+empId;
		}
		else
		{
			return "no data found with "+empId;
		}
		
		
	}

	@Override
	public List<EmployeeModel> findByDepartment(String department) {
		
		return employeeRepository.findByDepartment(department);
	}

	@Override
	public List<EmployeeModel> findAllEmployeeTop5SalariedEmployees(Integer number) {
		
		 List<EmployeeModel> allEmployees = employeeRepository.findAll();
		  return allEmployees.stream().sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))  // Sort by salary in descending order
         .limit(number)  // Limit to the top 5
         .collect(Collectors.toList());  
	}

	@Override
	public List<EmployeeModel> findAllEmployeeBasedOnSalaryRange(Double minSal, Double maxSal) {
		return employeeRepository.findBySalaryIn(minSal,maxSal);
	}

	@Override
	public List<EmployeeModel> findAllEmployeeBasedOnExperienceRange(String years) {
		log.info("findAllEmployeeBasedOnExperienceRange() called ");
		return employeeRepository.findAllEmployeeBasedOnExperienceRange(years);
		

	}

	@Override
	public CompletableFuture<Void> deleteAllEmployees() {
		
        return CompletableFuture.runAsync(() -> employeeRepository.deleteAll(),executorService);

	}

}
