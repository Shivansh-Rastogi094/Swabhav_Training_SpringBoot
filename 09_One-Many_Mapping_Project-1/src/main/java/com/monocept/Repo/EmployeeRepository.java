package com.monocept.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.monocept.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	boolean existsByEmail(String string);
	boolean existsByEmailAndIdNot(String string, long id);
	boolean existsByEmailAndDepartmentIdNot(String email, long departmentId);
}
