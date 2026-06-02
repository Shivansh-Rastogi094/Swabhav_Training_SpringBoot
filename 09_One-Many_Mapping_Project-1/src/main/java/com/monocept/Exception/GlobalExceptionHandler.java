package com.monocept.Exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<Map<String,Object>> handleDuplicateResource(DuplicateResourceException ex){
		log.error("Duplicate Resource Exception: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.CONFLICT.value());
		err.put("error", "Conflict");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String,Object>> handleResourceNotFound(ResourceNotFoundException ex){
		log.error("Resource Not Found: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.NOT_FOUND.value());
		err.put("error", "Not Found");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
		log.error("Validation error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.BAD_REQUEST.value());
		err.put("error", "Validation Error");
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		err.put("messages", errors);
		
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String,Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
		log.error("Type Mismatch error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.BAD_REQUEST.value());
		err.put("error", "Type Mismatch");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String,Object>> handleInvalidJson(HttpMessageNotReadableException ex){
		log.error("Invalid JSON error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.BAD_REQUEST.value());
		err.put("error", "Invalid JSON");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String,Object>> handleDataIntegrity(DataIntegrityViolationException ex){
		log.error("Data Integrity error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.CONFLICT.value());
		err.put("error", "Data Integrity Violation");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String,Object>> handleAccessDenied(AccessDeniedException ex){
		log.error("Access Denied: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.FORBIDDEN.value());
		err.put("error", "Forbidden");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String,Object>> handleIllegalArgument(IllegalArgumentException ex){
		log.error("Illegal Argument error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.BAD_REQUEST.value());
		err.put("error", "Bad Request");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,Object>> handleGeneric(Exception ex){
		log.error("Unexpected error: {}", ex.getMessage());
		Map<String,Object> err = new HashMap<>();
		err.put("timestamp", LocalDateTime.now());
		err.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		err.put("error", "Internal Server Error");
		err.put("message", ex.getMessage());
		
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
