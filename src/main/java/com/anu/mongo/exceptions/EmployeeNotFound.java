package com.anu.mongo.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeNotFound extends Exception{
	
	private static final long serialVersionUID = 1L;

	public EmployeeNotFound(String message) {
		super(message);
		log.info("EmployeeNotFound Object created "+message);	
	}

}
