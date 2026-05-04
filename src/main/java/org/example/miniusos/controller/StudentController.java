package org.example.miniusos.controller;

import jakarta.validation.Valid;
import org.example.miniusos.dto.course.ResponseCourseDetailDto;
import org.example.miniusos.dto.grade.CreateGradeDto;
import org.example.miniusos.dto.grade.ResponseGradeDto;
import org.example.miniusos.dto.student.CreateStudentDto;
import org.example.miniusos.dto.student.ResponseStudentDto;
import org.example.miniusos.dto.student.UpdateStudentDto;
import org.example.miniusos.service.GradeService;
import org.example.miniusos.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final GradeService gradeService;

    public StudentController(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseStudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStudentDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseStudentDto> addStudent(@Valid @RequestBody CreateStudentDto student) {
        ResponseStudentDto savedStudents = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseStudentDto> updateStudentById(@PathVariable Long id, @Valid @RequestBody UpdateStudentDto studentUpdates) {
        ResponseStudentDto updatedStudent = studentService.updateStudentById(id, studentUpdates);
        return ResponseEntity.ok(updatedStudent);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void> unenrollStudentFromCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        studentService.unenrollStudentFromCourse(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/grades")
    public ResponseEntity<ResponseGradeDto> addGradeToStudentById(@PathVariable Long studentId, @Valid @RequestBody CreateGradeDto gradeDto){
        ResponseGradeDto savedGrade = gradeService.addGrade(studentId, gradeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<ResponseGradeDto>> getAllGradesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(gradeService.getAllGradesByStudentId(studentId));
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<ResponseCourseDetailDto>> getAllCoursesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(studentService.getAllCoursesOfStudent(studentId));
    }
}
