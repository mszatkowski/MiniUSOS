package org.example.miniusos.controller;

import jakarta.validation.Valid;
import org.example.miniusos.dto.course.*;
import org.example.miniusos.dto.student.ResponseStudentDto;
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
    public ResponseEntity<List<ResponseCourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDto> getCourseById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PostMapping
    public ResponseEntity<ResponseCourseDto> addCourse(@Valid @RequestBody CreateCourseDto courseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDto));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<ResponseCourseDto> updateCourseById(@PathVariable Long courseId, @Valid @RequestBody UpdateCourseDto courseUpdates) {
        return ResponseEntity.ok(courseService.updateCourseById(courseId, courseUpdates));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long courseId){
        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseName}/students")
    public ResponseEntity<List<ResponseStudentDto>> getStudentsByCourse(@PathVariable String courseName) {
        return ResponseEntity.ok(courseService.getAllStudentsByCourseName(courseName));
    }
}
