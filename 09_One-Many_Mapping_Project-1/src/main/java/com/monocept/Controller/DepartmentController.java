package com.monocept.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.monocept.DTO.DepartmentRequestDto;
import com.monocept.DTO.DepartmentResponseDto;
import com.monocept.DTO.PageResponseDto;
import com.monocept.Exception.DuplicateResourceException;
import com.monocept.Exception.ResourceNotFoundException;
import com.monocept.Service.DepartmentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/departments")
@Validated
public class DepartmentController {

	private final DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	// CREATE DEPARTMENT
	@PostMapping
	public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto)
			throws DuplicateResourceException {

		log.info("Received request to create department: {}", requestDto.getDepartmentName());
		DepartmentResponseDto response = departmentService.createDepartment(requestDto);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// GET ALL DEPARTMENTS
	@GetMapping
	public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {

		log.info("Received request to fetch all departments");
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	// GET DEPARTMENTS WITH PAGINATION
	@GetMapping("/page")
	public ResponseEntity<PageResponseDto<DepartmentResponseDto>> getAllDepartmentsWithPagination(

			@RequestParam(defaultValue = "0") int pageNumber,

			@RequestParam(defaultValue = "5") int pageSize) {
		
		log.info("Received request to fetch departments with pagination: pageNumber={}, pageSize={}", pageNumber, pageSize);
		return ResponseEntity.ok(departmentService.getAllDepartmentsWithPagination(pageNumber, pageSize));
	}

	// GET DEPARTMENT BY ID
	@GetMapping("/{id}")
	public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable long id)
			throws ResourceNotFoundException {
		log.info("Received request to fetch department with id: {}", id);
		return ResponseEntity.ok(departmentService.getDepartmentById(id));
	}

	// UPDATE DEPARTMENT
	@PutMapping("/{id}")
	public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDto requestDto)
			throws ResourceNotFoundException, DuplicateResourceException {

		log.info("Received request to update department with id: {}",
		        id);
		return ResponseEntity.ok(departmentService.updateDepartment(id, requestDto));
	}

	// DELETE DEPARTMENT
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable long id) throws ResourceNotFoundException {
		log.info("Received request to delete department with id: {}",
		        id);
		departmentService.deleteDepartment(id);

		return ResponseEntity.noContent().build();
	}
}