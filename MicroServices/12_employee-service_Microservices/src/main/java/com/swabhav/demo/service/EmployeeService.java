package com.swabhav.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swabhav.demo.dto.EmployeeRequest;
import com.swabhav.demo.dto.EmployeeResponse;
import com.swabhav.demo.entity.Employee;
import com.swabhav.demo.exception.EmployeeNotFoundException;
import com.swabhav.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService {

    private  EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartmentId(request.getDepartmentId());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponse(savedEmployee);
    }

    public EmployeeResponse getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(employeeId)
                );

        return convertToResponse(employee);
    }

    public List<EmployeeResponse> getEmployeesByDepartmentId(
            Long departmentId) {

        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private EmployeeResponse convertToResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartmentId()
        );
    }
}
