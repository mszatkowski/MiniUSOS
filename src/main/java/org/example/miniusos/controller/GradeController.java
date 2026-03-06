package org.example.miniusos.controller;

import jakarta.validation.Valid;
import org.example.miniusos.dto.grade.ResponseGradeDto;
import org.example.miniusos.dto.grade.UpdateGradeDto;
import org.example.miniusos.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<Void> deleteGradeById(@PathVariable Long gradeId){
        gradeService.deleteGradeById(gradeId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{gradeId}")
    public ResponseEntity<ResponseGradeDto> updateGradeById(@PathVariable Long gradeId, @Valid @RequestBody UpdateGradeDto gradeUpdates) {
        return ResponseEntity.ok(gradeService.updateGradeById(gradeId, gradeUpdates));
    }
}
