package com.swabhav.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swabhav.demo.dto.CourseRequestDto;
import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentResponseDto;
import com.swabhav.demo.exception.BadRequestException;
import com.swabhav.demo.exception.DuplicateResourceException;
import com.swabhav.demo.exception.ResourceNotFoundException;
import com.swabhav.demo.model.Course;
import com.swabhav.demo.model.Student;
import com.swabhav.demo.repository.CourseRepository;
import com.swabhav.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        log.info("Creating course with code: {}", requestDto.getCourseCode());
        if (courseRepository.existsByCourseCode(requestDto.getCourseCode())) {
            throw new DuplicateResourceException("Course already exists with code: " + requestDto.getCourseCode());
        }
        Course savedCourse = courseRepository.save(modelMapper.map(requestDto, Course.class));
        log.info("Course created successfully with id: {}", savedCourse.getId());
        return modelMapper.map(savedCourse, CourseResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll().stream().map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<CourseResponseDto> getAllCoursesWithPagination(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        log.info("Fetching courses with pagination. pageNumber: {}, pageSize: {}, sortBy: {}, sortDirection: {}", pageNumber, pageSize, sortBy, sortDirection);
        validatePagination(pageNumber, pageSize);
        validateCourseSortField(sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(getSortDirection(sortDirection), sortBy));
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<CourseResponseDto> content = coursePage.getContent().stream().map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();
        return new PageResponseDto<>(content, coursePage.getNumber(), coursePage.getSize(), coursePage.getTotalElements(), coursePage.getTotalPages(), coursePage.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponseDto getCourseById(Long id) {
        log.info("Fetching course by id: {}", id);
        return modelMapper.map(findCourseById(id), CourseResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getStudentsByCourseId(Long courseId) {
        log.info("Fetching students enrolled in course id: {}", courseId);
        findCourseById(courseId);
        return studentRepository.findByCoursesId(courseId).stream().map(this::mapStudentToBasicResponseDto).toList();
    }

    @Override
    @Transactional
    public CourseResponseDto updateCourse(Long id, CourseRequestDto requestDto) {
        log.info("Updating course with id: {}", id);
        Course existingCourse = findCourseById(id);
        if (courseRepository.existsByCourseCodeAndIdNot(requestDto.getCourseCode(), id)) {
            throw new DuplicateResourceException("Course already exists with code: " + requestDto.getCourseCode());
        }
        existingCourse.setCourseName(requestDto.getCourseName());
        existingCourse.setCourseCode(requestDto.getCourseCode());
        existingCourse.setFees(requestDto.getFees());
        Course updatedCourse = courseRepository.save(existingCourse);
        log.info("Course updated successfully with id: {}", updatedCourse.getId());
        return modelMapper.map(updatedCourse, CourseResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        log.info("Deleting course with id: {}", id);
        Course course = findCourseById(id);
        Set<Student> enrolledStudents = new HashSet<>(course.getStudents());
        for (Student student : enrolledStudents) {
            student.removeCourse(course);
        }
        courseRepository.delete(course);
        log.info("Course deleted successfully with id: {}", id);
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private StudentResponseDto mapStudentToBasicResponseDto(Student student) {
        StudentResponseDto responseDto = new StudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setFullName(student.getFullName());
        responseDto.setEmail(student.getEmail());
        responseDto.setAge(student.getAge());
        responseDto.setCourses(student.getCourses().stream().map(course -> modelMapper.map(course, CourseResponseDto.class)).toList());
        return responseDto;
    }

    private void validatePagination(int pageNumber, int pageSize) {
        if (pageNumber < 0) throw new BadRequestException("Page number cannot be negative.");
        if (pageSize <= 0) throw new BadRequestException("Page size must be greater than 0.");
        if (pageSize > 100) throw new BadRequestException("Page size cannot be greater than 100.");
    }

    private void validateCourseSortField(String sortBy) {
        if (!List.of("id", "courseName", "courseCode", "fees").contains(sortBy)) {
            throw new BadRequestException("Invalid sort field for course: " + sortBy);
        }
    }

    private Sort.Direction getSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.equalsIgnoreCase("asc")) return Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) return Sort.Direction.DESC;
        throw new BadRequestException("Sort direction must be asc or desc.");
    }
}
