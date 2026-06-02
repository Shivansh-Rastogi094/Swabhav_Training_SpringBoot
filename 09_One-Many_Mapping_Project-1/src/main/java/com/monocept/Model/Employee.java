package com.monocept.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="employee_name")
	@NotBlank(message = "Name is required")
	private String employeeName;
	
	@Column(name="email", unique = true)
	@NotBlank(message = "Email is required")
	private String email;
	
	@Column(name="salary")
	@NotNull(message = "Salary cannot be blank")
	@Min(value = 1, message = "Salary should be greater than 0")
	private double salary;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
}
