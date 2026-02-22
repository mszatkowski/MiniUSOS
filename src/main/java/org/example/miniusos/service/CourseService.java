package org.example.miniusos.service;

import org.example.miniusos.dto.CourseDto;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.model.Course;
import org.example.miniusos.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public CourseDto getCourseById(Long id) {
        return mapToDto(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("course", id)));
    }

    public CourseDto addCourse(CourseDto courseDto) {
        Course course = new Course(courseDto.name(), courseDto.ectsPoints());
        course = courseRepository.save(course);
        return mapToDto(course);
    }

    public void deleteCourseById(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("course", id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDto mapToDto(Course course) {
        return new CourseDto(course.getId(), course.getName(), course.getEctsPoints());
    }
}
