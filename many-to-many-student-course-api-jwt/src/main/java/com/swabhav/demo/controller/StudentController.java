package com.swabhav.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentRequestDto;
import com.swabhav.demo.dto.StudentResponseDto;
import com.swabhav.demo.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Create student with courses")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto createStudent(@Valid @RequestBody StudentRequestDto requestDto) {
        log.info("API request received: create student");
        return studentService.createStudent(requestDto);
    }

    @Operation(summary = "Get all students")
    @GetMapping
    public List<StudentResponseDto> getAllStudents() {
        log.info("API request received: get all students");
        return studentService.getAllStudents();
    }

    @Operation(summary = "Get students with pagination and sorting")
    @GetMapping("/page")
    public PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        log.info("API request received: get students with pagination");
        return studentService.getAllStudentsWithPagination(pageNumber, pageSize, sortBy, sortDirection);
    }

    @Operation(summary = "Get courses assigned to a student")
    @GetMapping("/{studentId}/courses")
    public List<CourseResponseDto> getCoursesByStudentId(@PathVariable Long studentId) {
        log.info("API request received: get courses by student id: {}", studentId);
        return studentService.getCoursesByStudentId(studentId);
    }

    @Operation(summary = "Get student by id")
    @GetMapping("/{id}")
    public StudentResponseDto getStudentById(@PathVariable Long id) {
        log.info("API request received: get student by id: {}", id);
        return studentService.getStudentById(id);
    }

    @Operation(summary = "Update student with courses")
    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequestDto requestDto) {
        log.info("API request received: update student with id: {}", id);
        return studentService.updateStudent(id, requestDto);
    }

    @Operation(summary = "Assign course to student")
    @PutMapping("/{studentId}/courses/{courseId}")
    public StudentResponseDto assignCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        log.info("API request received: assign course to student");
        return studentService.assignCourseToStudent(studentId, courseId);
    }

    @Operation(summary = "Remove course from student")
    @DeleteMapping("/{studentId}/courses/{courseId}")
    public StudentResponseDto removeCourseFromStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        log.info("API request received: remove course from student");
        return studentService.removeCourseFromStudent(studentId, courseId);
    }

    @Operation(summary = "Delete student")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        log.info("API request received: delete student with id: {}", id);
        studentService.deleteStudent(id);
    }
}
