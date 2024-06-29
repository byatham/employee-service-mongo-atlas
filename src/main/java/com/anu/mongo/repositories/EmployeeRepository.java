package com.anu.mongo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.anu.mongo.model.EmployeeModel;

@Repository
public interface EmployeeRepository extends MongoRepository<EmployeeModel, String>{

	
	//@Query("{ id:?0 }")
	//Optional<EmployeeModel> findById(Integer empId);

	List<EmployeeModel> findByDepartment(String department);

	List<EmployeeModel> findBySalaryIn(Double minSal, Double maxSal);

	//@Query("{ 'years_of_experience' :?0 } ")
	
	@Query("{ 'yearsOfExperience' :?0 } ")

	List<EmployeeModel> findAllEmployeeBasedOnExperience(String years);

}
