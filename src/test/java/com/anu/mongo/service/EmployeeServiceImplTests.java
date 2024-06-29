package com.anu.mongo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.anu.mongo.exceptions.EmployeeNotFound;
import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.repositories.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeeServiceImplTests {
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	static List<EmployeeModel> empList=new ArrayList<>();
	
	static List<EmployeeModel> listWithSingle =new ArrayList<>();
	static EmployeeModel empRecordSingle;
	 
	 @BeforeEach
	    public void init() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @BeforeAll
	    static void setUpAll()
	    {
	    	empList=Arrays.asList(new EmployeeModel("1", "John", "Doe", "john.doe@example.com", "1234567890", "Male", 30, "Developer", "5", 10000.0, "IT"),
	    			new EmployeeModel("2", "Balu", "Doe", "balu.y@example.com", "1234567890", "Male", 30, "Recruiter", "9", 20000.0, "HR"),
	    			new EmployeeModel("3", "Jagan", "Doe", "jagan.doe@example.com", "1234567890", "Male", 30, "Tester", "15", 5000.0, "Testing")
	    			);
	    	
	    	EmployeeModel singleEmployee=	new EmployeeModel("1", "Nagaiah", "G", "nag.g@example.com", "1234567890", "Male", 35, "Developer", "20", 30000.0, "IT");
	    	listWithSingle = Arrays.asList(singleEmployee);
	    	
	    	empRecordSingle=new EmployeeModel("1", "Nagaiah", "G", "nag.g@example.com", "1234567890", "Male", 35, "Developer", "20", 30000.0, "IT");

	    }

	@Test
	void testSaveNewEmployee()
	{
		when(employeeRepository.save(empList.get(0))).thenReturn(empList.get(0));
		//test
		EmployeeModel savedEmployeed = employeeServiceImpl.saveNewEmployee(empList.get(0));
		
		
		//assert
		assertEquals(empList.get(0),savedEmployeed);
		
		//verify
		verify(employeeRepository, times(1)).save(empList.get(0));	
	}
	
	@Test
	void testFindSavedEmployeeById()
	{
		when(employeeRepository.findById(empList.get(0).getId())).thenReturn(Optional.of(empRecordSingle));
		EmployeeModel employeeFound = employeeServiceImpl.findSavedEmployeeById(empList.get(0).getId());
		assertEquals(empRecordSingle,  employeeFound);
		verify(employeeRepository, times(1)).findById(empList.get(0).getId());	
	}
	
	@Test
	void testFindAllEmployees()
	{
		when(employeeRepository.findAll()).thenReturn(empList);
		
		List<EmployeeModel> allEmployess = employeeServiceImpl.findAllEmployees();
		assertEquals(empList.size(),  allEmployess.size());
		verify(employeeRepository, times(1)).findAll();	
	}
	
		/*
		 * @Test void testUpdateExistingEmployee() throws EmployeeNotFound { Integer
		 * empId=1;
		 * when(employeeRepository.findById(empId.toString())).thenReturn(Optional.of(
		 * empRecordSingle));
		 * 
		 * EmployeeModel updateExistingEmployee =
		 * employeeServiceImpl.updateExistingEmployee(empRecordSingle);
		 * assertEquals(empRecordSingle, updateExistingEmployee);
		 * verify(employeeRepository, times(1)).findById(empId.toString()); }
		 */
	@Test
	void testFindByDepartment()
	{
		when(employeeRepository.findByDepartment(empList.get(0).getDepartment())).thenReturn(empList);
		
    List<EmployeeModel> findByDepartment = employeeServiceImpl.findByDepartment(empList.get(0).getDepartment());
		assertEquals(empList.get(0),  findByDepartment.get(0));
		verify(employeeRepository, times(1)).findByDepartment(empList.get(0).getDepartment());
	}
	
	@Test
	void testFindAllEmployeeTopSalariedEmployees()
	{
		when(employeeRepository.findAll()).thenReturn(empList);
		
  List<EmployeeModel> findAllEmployeeTop5SalariedEmployees = employeeServiceImpl.findAllEmployeeTopSalariedEmployees(1);
		assertEquals(20000.0 , findAllEmployeeTop5SalariedEmployees.get(0).getSalary());
		verify(employeeRepository, times(1)).findAll(); 
	}
	

	@Test
	void testFindAllEmployeeBasedOnSalaryRange()
	{
		when(employeeRepository.findBySalaryIn(19000.0, 20000.0)).thenReturn(listWithSingle);
		
  List<EmployeeModel> findAllEmployeeTopBasedOnSalarieRange = employeeServiceImpl.findAllEmployeeBasedOnSalaryRange(19000.0, 20000.0);
		assertEquals(1 , findAllEmployeeTopBasedOnSalarieRange.size());
		verify(employeeRepository, times(1)).findBySalaryIn(19000.0, 20000.0);
	}
	
	@Test
	void testFindAllEmployeeBasedOnExperience()
	{
		when(employeeRepository.findAllEmployeeBasedOnExperience("5")).thenReturn(listWithSingle);
		
  List<EmployeeModel> findAllEmployeeTopBasedExperience = employeeServiceImpl.findAllEmployeeBasedOnExperience("5");
		assertEquals(1 , findAllEmployeeTopBasedExperience.size());
		verify(employeeRepository, times(1)).findAllEmployeeBasedOnExperience("5");
	}

	@Test
	void testDeleteEmployeeById_RecordFound()
	{
		String empId=String.valueOf(1);
		 // Arrange
        when(employeeRepository.findById(empId)).thenReturn(Optional.of(empRecordSingle));
		
        String result = employeeServiceImpl.deleteEmployeeById(empId);
		assertEquals("your record has been deleted 1" , result);
		verify(employeeRepository, times(1)).deleteById(empId);
	}
	
	
	/*
	 * @Test void testDeleteEmployeeById_RecordNotFound() { Integer empId=400; //
	 * Arrange
	 * when(employeeRepository.findById(empId.toString())).thenReturn(Optional.empty
	 * ());
	 * 
	 * String result = employeeServiceImpl.deleteEmployeeById(empId);
	 * assertEquals("record not found with given empid "+empId , result);
	 * verify(employeeRepository, never()).deleteById(empId.toString()); }
	 */
	 /*
	@Test
	void testDeleteAllEmployees_Success()
	{
		 doNothing().when(employeeRepository).deleteAll();
	      // when(employeeRepository.deleteAll()).thenReturn(Optional.empty());

		// Call the method to verify the behavior
		 CompletableFuture<Void> result = employeeServiceImpl.deleteAllEmployees();

		 // Then: Verify the expected behavior
		 result.join(); // Wait for the CompletableFuture to complete
	        verify(employeeRepository, times(1)).deleteAll();
	}
	
	 @Test
	    public void testDeleteAllEmployees_ExceptionHandling() {
	        // Given: Setup mock to throw exception
	        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteAll();

	        // Capture logs
	       
	        // When: Call the method to be tested
	        CompletableFuture<Void> future = employeeServiceImpl.deleteAllEmployees();

	        // Then: Verify the expected behavior
	        future.join(); // Wait for the CompletableFuture to complete
	        verify(employeeRepository, times(1)).deleteAll();
	        //assertEquals("Exceptions occurred while processing data: ",future.g );
	    } */
	
}
