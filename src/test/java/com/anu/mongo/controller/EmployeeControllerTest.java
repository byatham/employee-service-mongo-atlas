package com.anu.mongo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.anu.mongo.model.EmployeeModel;
import com.anu.mongo.service.EmployeeService;
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    
    List<EmployeeModel> empList=new ArrayList<>();
    
    @BeforeAll
    public void setUpAll()
    {
    	empList=Arrays.asList(new EmployeeModel(1, "John", "Doe", "john.doe@example.com", "1234567890", "Male", 30, "Developer", "5", 10000.0, "IT"),
    			new EmployeeModel(2, "Balu", "Doe", "balu.y@example.com", "1234567890", "Male", 30, "Developer", "5", 20000.0, "IT"),
    			new EmployeeModel(4, "Jagan", "Doe", "jagan.doe@example.com", "1234567890", "Male", 30, "Tester", "5", 5000.0, "IT")
    			);
    }

    @Test
    public void testSaveEmployee() {
        when(employeeService.saveNewEmployee(any(EmployeeModel.class))).thenReturn(empList.get(0));

        ResponseEntity<EmployeeModel> savedEmployee = employeeController.saveEmployee(empList.get(0));
        

        assertEquals(HttpStatus.CREATED, savedEmployee.getStatusCode());
        assertEquals(empList.get(0), savedEmployee.getBody());

        verify(employeeService, times(1)).saveNewEmployee(any(EmployeeModel.class));
    }

    @Test
    public void testFindEmployeeById() {
        int empId = 1;
        when(employeeService.findSavedEmployeeById(empId)).thenReturn(empList.get(0));

        EmployeeModel foundEmployee = employeeController.findEmployeeByID(empId);

        assertEquals(empList.get(0), foundEmployee);

        verify(employeeService, times(1)).findSavedEmployeeById(empId);
    }



    @Test
       public void testFindAllEmployees() {
          
           when(employeeService.findAllEmployees()).thenReturn(empList);

           List<EmployeeModel> foundEmployees = employeeController.findAllEmployee();

           assertEquals(empList.size(), foundEmployees.size());
           assertEquals(empList.get(0), foundEmployees.get(0));
           assertEquals(empList.get(1), foundEmployees.get(1));

           verify(employeeService, times(1)).findAllEmployees();
       }

       @Test
       public void testUpdateEmployeeById() {
           when(employeeService.updateExistingEmployee(any(EmployeeModel.class))).thenReturn(empList.get(0));

           EmployeeModel updatedEmployee = employeeController.updateEmployeebyId(empList.get(0));

           assertEquals(empList.get(0), updatedEmployee);

           verify(employeeService, times(1)).updateExistingEmployee(any(EmployeeModel.class));
       }

       @Test
       public void testDeleteEmployeeById() {
           String empId = "1";
           when(employeeService.deleteEmployeeById(empId)).thenReturn("Employee with ID " + empId + " deleted");

           String result = employeeController.deleteEmployeeByID(empId);

           assertEquals("Employee with ID " + empId + " deleted", result);

           verify(employeeService, times(1)).deleteEmployeeById(empId);
       }

       @Test
       public void testFindByDepartment() {
           String department = "IT";
           when(employeeService.findByDepartment(department)).thenReturn(empList);

           List<EmployeeModel> foundEmployees = employeeController.findByDepartment(department);

           assertEquals(empList.size(), foundEmployees.size());
           assertEquals(empList.get(0), foundEmployees.get(0));
           assertEquals(empList.get(1), foundEmployees.get(1));

           verify(employeeService, times(1)).findByDepartment(department);
       }

       @Test
       public void testFindAllEmployeeTop5SalariedEmployees() {
           int number = 5;
           when(employeeService.findAllEmployeeTop5SalariedEmployees(number)).thenReturn(empList);

           List<EmployeeModel> topEmployees = employeeController.findAllEmployeeTop5SalariedEmployees(number);

           assertEquals(empList.size(), topEmployees.size());
           assertEquals(empList.get(0), topEmployees.get(0));
           assertEquals(empList.get(1), topEmployees.get(1));

           verify(employeeService, times(1)).findAllEmployeeTop5SalariedEmployees(number);
       }

       @Test
       public void testFindAllEmployeeBasedOnSalaryRange() {
           double minSalary = 5000.0;
           double maxSalary = 7000.0;
           when(employeeService.findAllEmployeeBasedOnSalaryRange(minSalary, maxSalary)).thenReturn(empList);

           List<EmployeeModel> filteredEmployees = employeeController.findAllEmployeeBasedOnSalaryRange(minSalary, maxSalary);

           assertEquals(empList.size(), filteredEmployees.size());
           assertEquals(empList.get(0), filteredEmployees.get(0));
           assertEquals(empList.get(1), filteredEmployees.get(1));

           verify(employeeService, times(1)).findAllEmployeeBasedOnSalaryRange(minSalary, maxSalary);
       }

       @Test
       public void testFindAllEmployeeBasedOnExperienceRange() {
           String years = "5";
           when(employeeService.findAllEmployeeBasedOnExperienceRange(years)).thenReturn(empList);

           List<EmployeeModel> filteredEmployees = employeeController.findAllEmployeeBasedOnExperienceRange(years);

           assertEquals(empList.size(), filteredEmployees.size());
           assertEquals(empList.get(0), filteredEmployees.get(0));
           assertEquals(empList.get(1), filteredEmployees.get(1));

           verify(employeeService, times(1)).findAllEmployeeBasedOnExperienceRange(years);
       }

       @Test
       public void testDeleteAllEmployees() throws ExecutionException, InterruptedException {
           CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null);
           when(employeeService.deleteAllEmployees()).thenReturn(completableFuture);

           CompletableFuture<ResponseEntity<Void>> result = employeeController.deleteAllEmployees();
           ResponseEntity<Void> responseEntity = result.get();

           assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

           verify(employeeService, times(1)).deleteAllEmployees();
       }
}
