package com.swabhav.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swabhav.demo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);

    boolean existsByCourseCodeAndIdNot(String courseCode, Long id);

    List<Course> findByStudentsId(Long studentId);
}
