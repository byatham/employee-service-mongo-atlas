package com.anu.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anu.mongo.model.EmployeeDataSession;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeSesssionDataController {

	@Autowired
	private EmployeeDataSession employeeDataSession;

	@GetMapping("/get/session/data")
	public String getEmployeeSessionData() {
		return employeeDataSession.getEmployeeName();
	}

}
