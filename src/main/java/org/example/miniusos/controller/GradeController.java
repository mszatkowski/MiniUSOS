package org.example.miniusos.controller;

import jakarta.validation.Valid;
import org.example.miniusos.dto.GradeDto;
import org.example.miniusos.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<GradeDto> addGradeToStudentById(@PathVariable Long studentId, @Valid @RequestBody GradeDto gradeDto){
        GradeDto savedGrade = gradeService.addGrade(studentId, gradeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<GradeDto>> getAllGradesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(gradeService.getAllGradesByStudentId(studentId));
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<Void> deleteGradeById(@PathVariable Long gradeId){
        gradeService.deleteGradeById(gradeId);
        return ResponseEntity.noContent().build();
    }
}
