package org.example.miniusos.service;

import org.example.miniusos.dto.GradeDto;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.model.Grade;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.GradeRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
    }

    public GradeDto addGrade(Long studentId, GradeDto gradeDto){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("student", studentId));

        Grade grade = new Grade(gradeDto.score(), gradeDto.subjectName(), student);
        grade = gradeRepository.save(grade);

        return mapToDto(grade);
    }

    public void deleteGradeById(Long id){
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException("grade", id);
        }
        gradeRepository.deleteById(id);
    }

    public List<GradeDto> getAllGradesByStudentId(Long studentId){
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private GradeDto mapToDto(Grade grade) {
        return new GradeDto(grade.getId(), grade.getScore(), grade.getSubjectName());
    }
}
