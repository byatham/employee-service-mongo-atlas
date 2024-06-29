package com.anu.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sd_employees")
public class EmployeeModel {
	
	@Id
	//private int id;
	private String id;
	
	@Field(name = "first_name")
	private String first_name;
	
	@Field(name = "last_name")
	private String last_name;
	
	@Field(name = "email")
	@Indexed(unique = true)
	private String email;
	
	@Field(name = "phone")
	private String phone;
	
	@Field(name = "gender")
	private String gender;
	
	@Field(name = "age")
	private int age;
	
	@Field(name = "job_title")
	private String job_title;
	
	@Field(name = "years_of_experience")
	private String years_of_experience;
	
	@Field(name = "salary")
	private Double salary;
	
	@Field(name = "department")
	private String  department;

}
