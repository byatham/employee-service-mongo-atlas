package com.anu.mongo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeRepository employeeRepository;
	private ExecutorService executor;
   // private final ExecutorService executorService = Executors.newFixedThreadPool(10);

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
		 
		 return employeeRepository.findAll();
	}
	

	@Override
	public EmployeeModel updateExistingEmployee(EmployeeModel updateModel) {
		
		return employeeRepository.save(updateModel);
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
	public List<EmployeeModel> findAllEmployeeBasedOnExperience(String years) {
		log.info("findAllEmployeeBasedOnExperienceRange() called ");
		return employeeRepository.findAllEmployeeBasedOnExperience(years);
		

	}
	
	@Override
	public String deleteEmployeeById(Integer empId) {
		Optional<EmployeeModel> recordFoundInDB = employeeRepository.findById(empId);
		
		if(recordFoundInDB.isPresent())
		{
			employeeRepository.deleteById(empId.toString());
			return "your record has been deleted "+empId;
			
		}
		else
		{
			return "record not found with given empid "+empId;

		}
		
		
	}
 /*
	@Override
	public CompletableFuture<Void> deleteAllEmployees() {
		
        //return CompletableFuture.runAsync(() -> employeeRepository.deleteAll(),executorService);
		
		return CompletableFuture.runAsync(() -> {
            try {
                employeeRepository.deleteAll();
            } catch (Exception e) {
                log.info("Exceptions occurred while processing data: " + e.getMessage());
            }
        }, executorService);

	} */

	@Override
	public List<Entry<String,Optional<EmployeeModel>>> maxSalariedEmployeesInDepartmentwise() {
		List<EmployeeModel> allEmployessList = employeeRepository.findAll();
		
		Map<String,Optional<EmployeeModel>> maxSalariedEmployees= allEmployessList.stream()
        .collect(Collectors.groupingBy(
            EmployeeModel::getDepartment,
            Collectors.maxBy(Comparator.comparingDouble(EmployeeModel::getSalary))
        ));
		
		maxSalariedEmployees.forEach((department, employee) -> {
            System.out.println("Department: " + department + ", Max Salaried Employee: " + employee.get());
        });
		
		List<Entry<String,Optional<EmployeeModel>>> employeesInEachDepartWithMaxSal = maxSalariedEmployees.entrySet()
		.stream()
		.collect(Collectors.toList());
		
		return employeesInEachDepartWithMaxSal;
		
	}
	
	@Override
	public List<Entry<String,Optional<EmployeeModel>>> minSalariedEmployeesInDepartmentwise() {
		List<EmployeeModel> allEmployessList = employeeRepository.findAll();
		
		Map<String,Optional<EmployeeModel>> minSalariedEmployees= allEmployessList.stream()
        .collect(Collectors.groupingBy(
            EmployeeModel::getDepartment,
            Collectors.minBy(Comparator.comparingDouble(EmployeeModel::getSalary))
        ));
		
		minSalariedEmployees.forEach((department, employee) -> {
            System.out.println("Department: " + department + ", Max Salaried Employee: " + employee.get());
        });
		
		List<Entry<String,Optional<EmployeeModel>>> employeesInEachDepartWithMinSal = minSalariedEmployees.entrySet()
		.stream()
		.collect(Collectors.toList());
		
		return employeesInEachDepartWithMinSal;
		
	}
	

}
