package com.anu.mongo.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

import com.anu.mongo.model.EmployeeModel;

public interface EmployeeService {

	EmployeeModel saveNewEmployee(EmployeeModel employeeModel);

	EmployeeModel findSavedEmployeeById(Integer empId);

	List<EmployeeModel> findAllEmployees();

	EmployeeModel updateExistingEmployee(EmployeeModel updateModel);

	String deleteEmployeeById(String empId);
	
	List<EmployeeModel> findByDepartment(String department);

	List<EmployeeModel> findAllEmployeeTop5SalariedEmployees(Integer number);

	List<EmployeeModel> findAllEmployeeBasedOnSalaryRange(Double minSal, Double maxSal);

	List<EmployeeModel> findAllEmployeeBasedOnExperienceRange(String years);

	CompletableFuture<Void> deleteAllEmployees();

}
