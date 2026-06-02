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

import com.swabhav.demo.dto.CourseRequestDto;
import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentResponseDto;
import com.swabhav.demo.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "Create course")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponseDto createCourse(@Valid @RequestBody CourseRequestDto requestDto) {
        log.info("API request received: create course");
        return courseService.createCourse(requestDto);
    }

    @Operation(summary = "Get all courses")
    @GetMapping
    public List<CourseResponseDto> getAllCourses() {
        log.info("API request received: get all courses");
        return courseService.getAllCourses();
    }

    @Operation(summary = "Get courses with pagination and sorting")
    @GetMapping("/page")
    public PageResponseDto<CourseResponseDto> getAllCoursesWithPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        log.info("API request received: get courses with pagination");
        return courseService.getAllCoursesWithPagination(pageNumber, pageSize, sortBy, sortDirection);
    }

    @Operation(summary = "Get students enrolled in a course")
    @GetMapping("/{courseId}/students")
    public List<StudentResponseDto> getStudentsByCourseId(@PathVariable Long courseId) {
        log.info("API request received: get students by course id: {}", courseId);
        return courseService.getStudentsByCourseId(courseId);
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/{id}")
    public CourseResponseDto getCourseById(@PathVariable Long id) {
        log.info("API request received: get course by id: {}", id);
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Update course")
    @PutMapping("/{id}")
    public CourseResponseDto updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDto requestDto) {
        log.info("API request received: update course with id: {}", id);
        return courseService.updateCourse(id, requestDto);
    }

    @Operation(summary = "Delete course")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable Long id) {
        log.info("API request received: delete course with id: {}", id);
        courseService.deleteCourse(id);
    }
}
