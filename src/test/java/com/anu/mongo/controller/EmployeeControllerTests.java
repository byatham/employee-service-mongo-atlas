package com.anu.mongo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.service.EmployeeService;

@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeControllerTests {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController employeeController;

	@Autowired
	private MockMvc mockMvc;

	private ExecutorService executorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	List<EmployeeModel> empList = new ArrayList<>();
	List<EmployeeModel> emptyList = new ArrayList<>();

	@BeforeAll
	public void setUpAll() {
		empList = Arrays.asList(
				new EmployeeModel(1, "John", "Doe", "john.doe@example.com", "1234567890", "Male", 30, "Developer", "5",
						10000.0, "IT"),
				new EmployeeModel(2, "Balu", "Doe", "balu.y@example.com", "1234567890", "Male", 30, "Developer", "5",
						20000.0, "IT"),
				new EmployeeModel(3, "Jagan", "Doe", "jagan.doe@example.com", "1234567890", "Male", 30, "Tester", "5",
						5000.0, "IT"));
	}

	@Test
     void testSaveEmployee() {
        when(employeeService.saveNewEmployee(any(EmployeeModel.class))).thenReturn(empList.get(0));

        ResponseEntity<EmployeeModel> savedEmployee = employeeController.saveEmployee(empList.get(0));
        

        assertEquals(HttpStatus.CREATED, savedEmployee.getStatusCode());
        assertEquals(empList.get(0), savedEmployee.getBody());

        verify(employeeService, times(1)).saveNewEmployee(any(EmployeeModel.class));
    }

	@Test
	void testFindEmployeeById() {
		int empId = 1;
		when(employeeService.findSavedEmployeeById(empId)).thenReturn(empList.get(0));

		EmployeeModel foundEmployee = employeeController.findEmployeeByID(empId);

		assertEquals(empList.get(0), foundEmployee);

		verify(employeeService, times(1)).findSavedEmployeeById(empId);
	}

	@Test
        void testFindAllEmployees() {
          
           when(employeeService.findAllEmployees()).thenReturn(empList);

           List<EmployeeModel> foundEmployees = employeeController.findAllEmployee();

           assertEquals(empList.size(), foundEmployees.size());
           assertEquals(empList.get(0), foundEmployees.get(0));
           assertEquals(empList.get(1), foundEmployees.get(1));

           verify(employeeService, times(1)).findAllEmployees();
       }

	@Test
        void testUpdateEmployeeById() {
           when(employeeService.updateExistingEmployee(any(EmployeeModel.class))).thenReturn(empList.get(0));

           EmployeeModel updatedEmployee = employeeController.updateEmployeebyId(empList.get(0));

           assertEquals(empList.get(0), updatedEmployee);

           verify(employeeService, times(1)).updateExistingEmployee(any(EmployeeModel.class));
       }

	@Test
	void testDeleteEmployeeById() {
		Integer empId = 1;
		when(employeeService.deleteEmployeeById(empId)).thenReturn("Employee with ID " + empId + " deleted");

		String result = employeeController.deleteEmployeeByID(empId);

		assertEquals("Employee with ID " + empId + " deleted", result);

		verify(employeeService, times(1)).deleteEmployeeById(empId);
	}

	@Test
	void testFindByDepartment() {
		String department = "IT";
		when(employeeService.findByDepartment(department)).thenReturn(empList);

		List<EmployeeModel> foundEmployees = employeeController.findByDepartment(department);

		assertEquals(empList.size(), foundEmployees.size());
		assertEquals(empList.get(0), foundEmployees.get(0));
		assertEquals(empList.get(1), foundEmployees.get(1));

		verify(employeeService, times(1)).findByDepartment(department);
	}

	@Test
	void testFindAllEmployeeTop5SalariedEmployees() {
		int number = 5;
		when(employeeService.findAllEmployeeTop5SalariedEmployees(number)).thenReturn(empList);

		List<EmployeeModel> topEmployees = employeeController.findAllEmployeeTop5SalariedEmployees(number);

		assertEquals(empList.size(), topEmployees.size());
		assertEquals(empList.get(0), topEmployees.get(0));
		assertEquals(empList.get(1), topEmployees.get(1));

		verify(employeeService, times(1)).findAllEmployeeTop5SalariedEmployees(number);
	}

	@Test
	void testFindAllEmployeeBasedOnSalaryRange() {
		double minSalary = 5000.0;
		double maxSalary = 7000.0;
		when(employeeService.findAllEmployeeBasedOnSalaryRange(minSalary, maxSalary)).thenReturn(empList);

		List<EmployeeModel> filteredEmployees = employeeController.findAllEmployeeBasedOnSalaryRange(minSalary,
				maxSalary);

		assertEquals(empList.size(), filteredEmployees.size());
		assertEquals(empList.get(0), filteredEmployees.get(0));
		assertEquals(empList.get(1), filteredEmployees.get(1));

		verify(employeeService, times(1)).findAllEmployeeBasedOnSalaryRange(minSalary, maxSalary);
	}

	@Test
	void testFindAllEmployeeBasedOnExperienceRange() {
		String years = "5";
		when(employeeService.findAllEmployeeBasedOnExperience(years)).thenReturn(empList);

		List<EmployeeModel> filteredEmployees = employeeController.findAllEmployeeBasedOnExperienceRange(years);

		assertEquals(empList.size(), filteredEmployees.size());
		assertEquals(empList.get(0), filteredEmployees.get(0));
		assertEquals(empList.get(1), filteredEmployees.get(1));

		verify(employeeService, times(1)).findAllEmployeeBasedOnExperience(years);
	}

	/*
	 * @Test void testDeleteAllEmployees_Success1() throws ExecutionException,
	 * InterruptedException { CompletableFuture<Void> completableFuture =
	 * CompletableFuture.completedFuture(null);
	 * when(employeeService.deleteAllEmployees()).thenReturn(completableFuture);
	 * 
	 * String resultStatus = employeeController.deleteAllEmployees();
	 * 
	 * assertEquals("Your entire table data has been deleted", resultStatus);
	 * 
	 * verify(employeeService, times(1)).deleteAllEmployees(); }
	 * 
	 * @Test void testDeleteAllEmployees_NoSuccess1() throws ExecutionException,
	 * InterruptedException { CompletableFuture<Void> completableFuture =
	 * CompletableFuture.completedFuture(null);
	 * when(employeeService.deleteAllEmployees()).thenReturn(completableFuture);
	 * 
	 * String resultStatus = employeeController.deleteAllEmployees();
	 * 
	 * assertEquals("Your table data not able to delete", resultStatus);
	 * 
	 * verify(employeeService, times(1)).deleteAllEmployees();
	 * 
	 * }
	 * 
	 * @Test public void testDeleteAllEmployees_Success() throws Exception { //
	 * Given CompletableFuture<Void> completableFuture =
	 * CompletableFuture.runAsync(() -> {}, executorService);
	 * Mockito.when(employeeService.deleteAllEmployees()).thenReturn(
	 * completableFuture); completableFuture.complete(null); // Mark the future as
	 * done
	 * 
	 * // When and Then mockMvc.perform(delete("/delete/all"))
	 * .andExpect(status().isOk())
	 * .andExpect(content().string("Your entire table data has been deleted")); }
	 * 
	 * @Test public void testDeleteAllEmployees_Failure() throws Exception { //
	 * Given CompletableFuture<Void> completableFuture =
	 * CompletableFuture.runAsync(() -> {}, executorService);
	 * Mockito.when(employeeService.deleteAllEmployees()).thenReturn(
	 * completableFuture);
	 * 
	 * // When and Then mockMvc.perform(delete("/delete/all"))
	 * .andExpect(status().isOk())
	 * .andExpect(content().string("Your table data not able to delete")); }
	 */

}
