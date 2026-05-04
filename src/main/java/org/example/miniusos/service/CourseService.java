package org.example.miniusos.service;

import org.example.miniusos.dto.course.*;
import org.example.miniusos.dto.student.ResponseStudentDto;
import org.example.miniusos.exception.DuplicateResourceException;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.mappers.CourseDetailMapper;
import org.example.miniusos.mappers.StudentMapper;
import org.example.miniusos.model.CourseDetail;
import org.example.miniusos.repository.CourseDetailRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseService {

    private final CourseDetailRepository courseDetailRepository;
    private final CourseDetailMapper courseDetailMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public CourseService(CourseDetailRepository courseDetailRepository, CourseDetailMapper courseDetailMapper, StudentRepository studentRepository, StudentMapper studentMapper) {
        this.courseDetailRepository = courseDetailRepository;
        this.courseDetailMapper = courseDetailMapper;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public List<ResponseCourseDetailDto> getAllCourses() {
        return courseDetailRepository.findAll()
                .stream()
                .map(courseDetailMapper::toDto)
                .toList();
    }

    public ResponseCourseDetailDto getCourseById(Long id) {
        return courseDetailMapper.toDto(courseDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CourseDetail.class, id)));
    }

    @Transactional
    public ResponseCourseDetailDto addCourse(CreateCourseDetailDto courseDto) {
        if (courseDetailRepository.existsByName(courseDto.name())) {
            throw new DuplicateResourceException(CourseDetail.class, "name", courseDto.name());
        }
        CourseDetail courseDetail = courseDetailMapper.toEntity(courseDto);
        courseDetail = courseDetailRepository.save(courseDetail);

        return courseDetailMapper.toDto(courseDetail);
    }

    @Transactional
    public void deleteCourseById(Long id) {
        if (!courseDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException(CourseDetail.class, id);
        }
        courseDetailRepository.deleteById(id);
    }

    @Transactional
    public ResponseCourseDetailDto updateCourseById(Long id, UpdateCourseDetailDto courseUpdates) {
        CourseDetail courseDetail = courseDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CourseDetail.class, id));

        if (courseUpdates.name() != null && !courseUpdates.name().equals(courseDetail.getName())) {
            if (courseDetailRepository.existsByName(courseUpdates.name())) {
                throw new DuplicateResourceException(CourseDetail.class, "name", courseUpdates.name());
            }
        }

        courseDetailMapper.updateEntityFromDto(courseUpdates, courseDetail);
        courseDetail = courseDetailRepository.save(courseDetail);

        return courseDetailMapper.toDto(courseDetail);
    }

    public List<ResponseStudentDto> getAllStudentsByCourseName(String courseName) {
        return studentRepository.findByEnrollmentsCourseName(courseName).stream()
                .map(studentMapper::toDto)
                .toList();
    }
}
