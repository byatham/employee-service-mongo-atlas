package com.anu.mongo.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sd/api")
@Slf4j
public class EmployeeController {

	private EmployeeService employeeService;
   @Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;

	}

	@PostMapping("/save")
	public EmployeeModel saveEmployee(@RequestBody EmployeeModel employeeModel) {
		return employeeService.saveNewEmployee(employeeModel);
	}

	@GetMapping("/find/{empId}")
	public EmployeeModel findEmployeeByID(@PathVariable Integer empId) {
		return employeeService.findSavedEmployeeById(empId);
	}

	@GetMapping("/find/all")
	//@PostMapping("/find/all")//MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: "all"]
	public List<EmployeeModel> findAllEmployee() {
		return employeeService.findAllEmployees();
	}

	@PutMapping("/update/")
	public EmployeeModel updateEmployeebyId(@RequestBody EmployeeModel updateModel) {
		return employeeService.updateExistingEmployee(updateModel);
	}

	@GetMapping("/delete/{empId}")
	public String deleteEmployeeByID(@PathVariable String empId) {
		return employeeService.deleteEmployeeById(empId);
	}
	
	@GetMapping("/department/{department}")
	public List<EmployeeModel> findByDepartment(@PathVariable String department) {
		
		return employeeService.findByDepartment(department);
	}
	
	
	//top salaried employees based on user input
	@GetMapping("/topSalaries/{number}")
	public List<EmployeeModel> findAllEmployeeTop5SalariedEmployees(@PathVariable Integer number) {
		return employeeService.findAllEmployeeTop5SalariedEmployees(number);
	}
	
	  // salaried  range employees based on user input
		@GetMapping("/salaryRange/{minSal}/{maxSal}")
		public List<EmployeeModel> findAllEmployeeBasedOnSalaryRange(@PathVariable Double minSal,@PathVariable Double maxSal) {
			return employeeService.findAllEmployeeBasedOnSalaryRange(minSal,maxSal);
		}
	
		 // years of experience employees based on user input
		@GetMapping("/experience/{years}")
		public List<EmployeeModel> findAllEmployeeBasedOnExperienceRange(@PathVariable String years) {
			log.info("findAllEmployeeBasedOnExperienceRange() called ");
			return employeeService.findAllEmployeeBasedOnExperienceRange(years);
		}
		
		@DeleteMapping("/delete/all")
	    public CompletableFuture<ResponseEntity<Void>> deleteAllEmployees() {
	        return employeeService.deleteAllEmployees().thenApply(aVoid -> ResponseEntity.noContent().build());
	    }

}
