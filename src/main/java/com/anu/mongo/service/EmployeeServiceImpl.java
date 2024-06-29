package com.anu.mongo.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.anu.mongo.exceptions.EmployeeNotFound;
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
	@CacheEvict(value = "employees", allEntries = true)
	public EmployeeModel findSavedEmployeeById(String empId) {
		return employeeRepository.findById(empId).get();
	}

	@Override
	@Cacheable(value = "employees")
	public List<EmployeeModel> findAllEmployees() {
		try {
			Thread.sleep(2000);
			 List<EmployeeModel> allEmployeesInDb = employeeRepository.findAll();
			  log.info("All employees from backend "+allEmployeesInDb);
			  return allEmployeesInDb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
		 
	}

	@Override
	public EmployeeModel updateExistingEmployee(EmployeeModel updateModel)throws EmployeeNotFound {
		log.info("Before upadet record:"+updateModel);
		try
		{
		EmployeeModel empExistingRecord = employeeRepository.findById(updateModel.getId()).get();
		if(!ObjectUtils.isEmpty(empExistingRecord))
		{
			empExistingRecord.setFirst_name(updateModel.getFirst_name());
			empExistingRecord.setLast_name(updateModel.getLast_name());
			empExistingRecord.setEmail(updateModel.getEmail());
			empExistingRecord.setDepartment(updateModel.getDepartment());
			empExistingRecord.setJob_title(updateModel.getJob_title());
			empExistingRecord.setSalary(updateModel.getSalary());
			empExistingRecord.setYears_of_experience(updateModel.getYears_of_experience());
			log.info("after updating fields before saving record ");
			return employeeRepository.save(empExistingRecord);
		}
		}catch (Exception e) {
			throw new EmployeeNotFound("Emp not found in db to update ");
		}
		throw new EmployeeNotFound("Emp not found in db to update ");
	}

	@Override
	public List<EmployeeModel> findByDepartment(String department) {
		
		return employeeRepository.findByDepartment(department);
	}

	@Override
	public List<EmployeeModel> findAllEmployeeTopSalariedEmployees(Integer number) {
		
		 List<EmployeeModel> allEmployees = employeeRepository.findAll();
		 log.info("All employees before apply salaries conditions "+allEmployees);
		  return allEmployees.stream().sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))  // Sort by salary in descending order
         .limit(number)  // Limit to the top number
         .collect(Collectors.toList());
		  
	}

	@Override
	public List<EmployeeModel> findAllEmployeeBasedOnSalaryRange(Double minSal, Double maxSal) {
		 List<EmployeeModel> employeesInGiveSalCondition = employeeRepository.findBySalaryIn(minSal,maxSal);
		 log.info("employeesInGiveSalCondition :: "+employeesInGiveSalCondition);
		 return employeesInGiveSalCondition;
		 
	}

	@Override
	public List<EmployeeModel> findAllEmployeeBasedOnExperience(String years) {
		log.info("findAllEmployeeBasedOnExperienceRange() called ");
		return employeeRepository.findAllEmployeeBasedOnExperience(years);
		

	}
	
	@Override
	@Cacheable(value = "employees", key = "#a0")
	public String deleteEmployeeById(String empId) {
		Optional<EmployeeModel> recordFoundInDB = employeeRepository.findById(empId);
		log.info("DeleteEmployeeByID() called ***** "+recordFoundInDB.get());
		if(recordFoundInDB.isPresent())
		{
			employeeRepository.deleteById(empId);
			log.info("your record has been deleted:"+empId);
			return "your record has been deleted "+empId;
			
		}
		else
		{
			log.info("record not found with given empid:"+empId);

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
		log.info("Before processing record on department and maxsal condition");
		
		maxSalariedEmployees.forEach((department, employee) -> {
            log.info("Department: " + department + ", Max Salaried Employee: " + employee.get());
        });
		
		List<Entry<String,Optional<EmployeeModel>>> employeesInEachDepartWithMaxSal = maxSalariedEmployees.entrySet()
		.stream()
		.collect(Collectors.toList());
		log.info("After processing record on department and maxsalary condition");
		log.info("EmployeesInEachDepartWithMaxSal"+employeesInEachDepartWithMaxSal);
		
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
		log.info("Before processing record on department and salary minsal condition");
		minSalariedEmployees.forEach((department, employee) -> {
            log.info("Department: " + department + ", min Salaried Employee: " + employee.get());
        });
		
		List<Entry<String,Optional<EmployeeModel>>> employeesInEachDepartWithMinSal = minSalariedEmployees.entrySet()
		.stream()
		.collect(Collectors.toList());
		log.info("After processing record on department and minsalary condition");
		log.info("EmployeesInEachDepartWithMinSal"+employeesInEachDepartWithMinSal);
		
		return employeesInEachDepartWithMinSal;
	}
	

}
