package org.example.miniusos.controller;

import jakarta.validation.Valid;
import org.example.miniusos.dto.CourseDto;
import org.example.miniusos.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseDto> addCourse(@Valid @RequestBody CourseDto courseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDto));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<CourseDto> deleteCourseById(@PathVariable Long courseId){
        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }
}
