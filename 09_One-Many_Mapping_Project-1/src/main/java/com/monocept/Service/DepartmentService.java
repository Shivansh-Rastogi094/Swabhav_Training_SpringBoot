package com.monocept.Service;

import java.util.List;

import com.monocept.DTO.DepartmentRequestDto;
import com.monocept.DTO.DepartmentResponseDto;
import com.monocept.DTO.PageResponseDto;
import com.monocept.Exception.DuplicateResourceException;
import com.monocept.Exception.ResourceNotFoundException;
public interface DepartmentService {
	public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) throws DuplicateResourceException;
	public List<DepartmentResponseDto> getAllDepartments();
	public PageResponseDto<DepartmentResponseDto> getAllDepartmentsWithPagination(int pageNumber, int pageSize);
	public DepartmentResponseDto getDepartmentById(long id) throws ResourceNotFoundException;
//	public DepartmentResponseDto updateDepartment(DepartmentRequestDto requestDto) throws ResourceNotFoundException, DuplicateResourceException;
	public String deleteDepartment(long id) throws ResourceNotFoundException;
	DepartmentResponseDto updateDepartment(Long departmentId, DepartmentRequestDto requestDto)
			throws ResourceNotFoundException, DuplicateResourceException;
}
