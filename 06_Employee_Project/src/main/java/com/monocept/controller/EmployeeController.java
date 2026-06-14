package com.monocept.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.dto.EmployeeResponseDTO;
import com.monocept.exception.EmpNotFound;
import com.monocept.model.Employee;
import com.monocept.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(value="http://localhost:5173/")
public class EmployeeController {

	private EmployeeService empService;
	
	@Autowired
	public EmployeeController(EmployeeService empService) {
		this.empService=empService;
	}
	
	@GetMapping("/trial")
	public String checkingConnection() {
		return ("Connection established"); 
	}
	
	@GetMapping("/fetchAll")
	public List<EmployeeResponseDTO> fetchAllEmp(){
		List<EmployeeResponseDTO> allEmp =empService.fetchAllEmp();	
		return allEmp;
		}
	
	@GetMapping("/fetch/id={id}")
	public EmployeeResponseDTO fetchById(@PathVariable int id) throws EmpNotFound{
		return empService.fetchById(id);
	}
	
	@PostMapping("/create")
	public EmployeeResponseDTO createEmp(@RequestBody Employee emp) {
		return empService.createEmp(emp);
	}
	
	@PutMapping("/updateVia/{id}")
	public EmployeeResponseDTO updateVia(@PathVariable int id, @RequestBody Employee emp) throws EmpNotFound {
		return empService.updateVia(id, emp);
	
	}
	@DeleteMapping("/delete/{id}")
	public String deleteById (@PathVariable int id) throws EmpNotFound {
	return empService.deleteById(id);	}
}
