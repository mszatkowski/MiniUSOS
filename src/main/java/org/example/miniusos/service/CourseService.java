package org.example.miniusos.service;

import org.example.miniusos.dto.course.*;
import org.example.miniusos.dto.student.ResponseStudentDto;
import org.example.miniusos.exception.DuplicateResourceException;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.mappers.CourseMapper;
import org.example.miniusos.mappers.StudentMapper;
import org.example.miniusos.model.Course;
import org.example.miniusos.repository.CourseRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, StudentRepository studentRepository, StudentMapper studentMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public List<ResponseCourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDto)
                .toList();
    }

    public ResponseCourseDto getCourseById(Long id) {
        return courseMapper.toDto(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, id)));
    }

    @Transactional
    public ResponseCourseDto addCourse(CreateCourseDto courseDto) {
        if (courseRepository.existsByName(courseDto.name())) {
            throw new DuplicateResourceException(Course.class, "name", courseDto.name());
        }
        Course course = courseMapper.toEntity(courseDto);
        course = courseRepository.save(course);

        return courseMapper.toDto(course);
    }

    @Transactional
    public void deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException(Course.class, id);
        }
        courseRepository.deleteById(id);
    }

    @Transactional
    public ResponseCourseDto updateCourseById(Long id, UpdateCourseDto courseUpdates) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, id));

        if (courseUpdates.name() != null && !courseUpdates.name().equals(course.getName())) {
            if (courseRepository.existsByName(courseUpdates.name())) {
                throw new DuplicateResourceException(Course.class, "name", courseUpdates.name());
            }
        }

        courseMapper.updateEntityFromDto(courseUpdates, course);
        course = courseRepository.save(course);

        return courseMapper.toDto(course);
    }

    public List<ResponseStudentDto> getAllStudentsByCourseName(String courseName) {
        return studentRepository.findByEnrollmentsCourseName(courseName).stream()
                .map(studentMapper::toDto)
                .toList();
    }
}
