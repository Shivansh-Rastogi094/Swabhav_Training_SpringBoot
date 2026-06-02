package com.swabhav.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swabhav.demo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    List<Student> findByCoursesId(Long courseId);
}
