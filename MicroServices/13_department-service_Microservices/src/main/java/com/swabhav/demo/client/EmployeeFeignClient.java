package com.swabhav.demo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.swabhav.demo.dto.EmployeeResponse;

@FeignClient(
    name = "employee-service",
    url ="${employee.service.url}"
)
public interface EmployeeFeignClient {
 
    @GetMapping("/employees/department/{departmentId}")
    List<EmployeeResponse> getEmployeeByDepartmentId(@PathVariable("departmentId") long departmentId);
    
}