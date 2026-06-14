package com.swabhav.demo.service;

import java.util.List;

import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentRequestDto;
import com.swabhav.demo.dto.StudentResponseDto;

public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto requestDto);

    List<StudentResponseDto> getAllStudents();

    PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(int pageNumber, int pageSize, String sortBy, String sortDirection);

    StudentResponseDto getStudentById(Long id);

    List<CourseResponseDto> getCoursesByStudentId(Long studentId);

    StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto);

    StudentResponseDto assignCourseToStudent(Long studentId, Long courseId);

    StudentResponseDto removeCourseFromStudent(Long studentId, Long courseId);

    void deleteStudent(Long id);
}
