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

import com.swabhav.demo.dto.CourseResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentRequestDto;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        log.info("Creating student with email: {}", requestDto.getEmail());
        if (studentRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Student already exists with email: " + requestDto.getEmail());
        }
        validateDuplicateCourseIds(requestDto.getCourseIds());
        Student student = new Student();
        student.setFullName(requestDto.getFullName());
        student.setEmail(requestDto.getEmail());
        student.setAge(requestDto.getAge());
        for (Course course : getCoursesFromIds(requestDto.getCourseIds())) {
            student.addCourse(course);
        }
        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with id: {}", savedStudent.getId());
        return mapStudentToResponseDto(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDto> getAllStudents() {
        log.info("Fetching all students");
        return studentRepository.findAll().stream().map(this::mapStudentToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        log.info("Fetching students with pagination. pageNumber: {}, pageSize: {}, sortBy: {}, sortDirection: {}", pageNumber, pageSize, sortBy, sortDirection);
        validatePagination(pageNumber, pageSize);
        validateStudentSortField(sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(getSortDirection(sortDirection), sortBy));
        Page<Student> studentPage = studentRepository.findAll(pageable);
        List<StudentResponseDto> content = studentPage.getContent().stream().map(this::mapStudentToResponseDto).toList();
        return new PageResponseDto<>(content, studentPage.getNumber(), studentPage.getSize(), studentPage.getTotalElements(), studentPage.getTotalPages(), studentPage.isLast());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        log.info("Fetching student by id: {}", id);
        return mapStudentToResponseDto(findStudentById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getCoursesByStudentId(Long studentId) {
        log.info("Fetching courses assigned to student id: {}", studentId);
        findStudentById(studentId);
        return courseRepository.findByStudentsId(studentId).stream().map(course -> modelMapper.map(course, CourseResponseDto.class)).toList();
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {
        log.info("Updating student with id: {}", id);
        Student existingStudent = findStudentById(id);
        if (studentRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)) {
            throw new DuplicateResourceException("Student already exists with email: " + requestDto.getEmail());
        }
        validateDuplicateCourseIds(requestDto.getCourseIds());
        existingStudent.setFullName(requestDto.getFullName());
        existingStudent.setEmail(requestDto.getEmail());
        existingStudent.setAge(requestDto.getAge());
        replaceStudentCourses(existingStudent, getCoursesFromIds(requestDto.getCourseIds()));
        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Student updated successfully with id: {}", updatedStudent.getId());
        return mapStudentToResponseDto(updatedStudent);
    }

    @Override
    @Transactional
    public StudentResponseDto assignCourseToStudent(Long studentId, Long courseId) {
        log.info("Assigning course id: {} to student id: {}", courseId, studentId);
        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);
        boolean alreadyAssigned = student.getCourses().stream().anyMatch(existingCourse -> existingCourse.getId().equals(courseId));
        if (alreadyAssigned) throw new BadRequestException("Course is already assigned to this student.");
        student.addCourse(course);
        return mapStudentToResponseDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public StudentResponseDto removeCourseFromStudent(Long studentId, Long courseId) {
        log.info("Removing course id: {} from student id: {}", courseId, studentId);
        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);
        boolean assigned = student.getCourses().stream().anyMatch(existingCourse -> existingCourse.getId().equals(courseId));
        if (!assigned) throw new BadRequestException("Course is not assigned to this student.");
        student.removeCourse(course);
        return mapStudentToResponseDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.info("Deleting student with id: {}", id);
        Student student = findStudentById(id);
        Set<Course> assignedCourses = new HashSet<>(student.getCourses());
        for (Course course : assignedCourses) {
            student.removeCourse(course);
        }
        studentRepository.delete(student);
        log.info("Student deleted successfully with id: {}", id);
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private Set<Course> getCoursesFromIds(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) return new HashSet<>();
        Set<Course> courses = new HashSet<>();
        for (Long courseId : courseIds) courses.add(findCourseById(courseId));
        return courses;
    }

    private void replaceStudentCourses(Student student, Set<Course> newCourses) {
        Set<Course> oldCourses = new HashSet<>(student.getCourses());
        for (Course oldCourse : oldCourses) student.removeCourse(oldCourse);
        for (Course newCourse : newCourses) student.addCourse(newCourse);
    }

    private void validateDuplicateCourseIds(List<Long> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) return;
        if (new HashSet<>(courseIds).size() != courseIds.size()) throw new BadRequestException("Duplicate course IDs are not allowed.");
    }

    private StudentResponseDto mapStudentToResponseDto(Student student) {
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

    private void validateStudentSortField(String sortBy) {
        if (!List.of("id", "fullName", "email", "age").contains(sortBy)) {
            throw new BadRequestException("Invalid sort field for student: " + sortBy);
        }
    }

    private Sort.Direction getSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.equalsIgnoreCase("asc")) return Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) return Sort.Direction.DESC;
        throw new BadRequestException("Sort direction must be asc or desc.");
    }
}
