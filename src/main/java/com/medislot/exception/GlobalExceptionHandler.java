package com.medislot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import  com.medislot.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

// this class handle exception for All controller
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerNotFound(ResourceNotFoundException ex, 
			HttpServletRequest request){
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
		
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handlerDuplicate(DuplicateResourceException ex, 
			HttpServletRequest request){
		return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
		
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handlerBadRequest(BadRequestException ex, 
			HttpServletRequest request){
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
		
	}
	
	// any other unexpected exception ends up here
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerGeneric(Exception ex, 
			HttpServletRequest request){
		ex.printStackTrace();
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", request);
		
	}
	
	// common helper so we don't repeat the same lines4 times
	private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request){
		ErrorResponse body = new ErrorResponse(
				status.value(),  // e.g. 404
				status.getReasonPhrase(),
				message,
				request.getRequestURI());
		
		return new ResponseEntity<>(body, status);
		
	}
}
