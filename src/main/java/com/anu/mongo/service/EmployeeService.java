package com.anu.mongo.service;

import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;

import com.anu.mongo.exceptions.EmployeeNotFound;
import com.anu.mongo.model.EmployeeModel;

public interface EmployeeService {

	EmployeeModel saveNewEmployee(EmployeeModel employeeModel);

	EmployeeModel findSavedEmployeeById(Integer empId);

	List<EmployeeModel> findAllEmployees();

	EmployeeModel updateExistingEmployee(EmployeeModel updateModel) throws EmployeeNotFound;

	String deleteEmployeeById(Integer empId);
	
	List<EmployeeModel> findByDepartment(String department);

	List<EmployeeModel> findAllEmployeeTopSalariedEmployees(Integer number);

	List<EmployeeModel> findAllEmployeeBasedOnSalaryRange(Double minSal, Double maxSal);

	List<EmployeeModel> findAllEmployeeBasedOnExperience(String years);

	List<Entry<String,Optional<EmployeeModel>>>  maxSalariedEmployeesInDepartmentwise();
	List<Entry<String,Optional<EmployeeModel>>>  minSalariedEmployeesInDepartmentwise();

	//CompletableFuture<Void> deleteAllEmployees();

}
