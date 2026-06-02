package com.monocept.Model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	
	@Column(name="department_name", unique = true)
	@NotBlank(message = "Name is required")
	private String departmentName;
	
	@Column(name="location")
	@NotBlank(message = "Location is required")
	private String location;
	
	@OneToMany(
			mappedBy = "department",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	private List<Employee> employees;

}
