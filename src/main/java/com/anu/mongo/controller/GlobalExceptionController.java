package com.anu.mongo.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.anu.mongo.exceptions.EmployeeNotFound;
import com.anu.mongo.exceptions.ErrorDetails;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController{
	public GlobalExceptionController() {
		log.info("GlobalExceptionController Object is created >>>>>>> ");
	}

	@ExceptionHandler(EmployeeNotFound.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(EmployeeNotFound ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	
	  @ExceptionHandler(Exception.class) public ResponseEntity<ErrorDetails>
	  handleGlobalException(Exception ex, WebRequest request) {
	  log.info("handleGlobalException() calling ***** ");
	  ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	  request.getDescription(false));
	  return new ResponseEntity<>(errorDetails,
	  HttpStatus.BAD_REQUEST); }
	 
}