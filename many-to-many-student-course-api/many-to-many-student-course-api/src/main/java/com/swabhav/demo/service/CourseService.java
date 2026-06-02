package com.swabhav.demo.service;

import java.util.List;

import com.swabhav.demo.dto.CourseRequestDto;
import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentResponseDto;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto requestDto);

    List<CourseResponseDto> getAllCourses();

    PageResponseDto<CourseResponseDto> getAllCoursesWithPagination(int pageNumber, int pageSize, String sortBy, String sortDirection);

    CourseResponseDto getCourseById(Long id);

    List<StudentResponseDto> getStudentsByCourseId(Long courseId);

    CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto);

    void deleteCourse(Long id);
}
