package org.example.miniusos.service;

import org.example.miniusos.dto.grade.*;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.exception.StudentNotEnrolledException;
import org.example.miniusos.mappers.GradeMapper;
import org.example.miniusos.model.Course;
import org.example.miniusos.model.Enrollment;
import org.example.miniusos.model.Grade;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.CourseRepository;
import org.example.miniusos.repository.GradeRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeMapper gradeMapper;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository, GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeMapper = gradeMapper;
    }

    @Transactional
    public ResponseGradeDto addGrade(Long studentId, CreateGradeDto gradeDto){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));
        Course course = courseRepository.findById(gradeDto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, gradeDto.courseId()));

        boolean isEnrolled = student.getEnrollments().stream()
                .map(Enrollment::getCourse)
                .anyMatch(course::equals);

        if (!isEnrolled) {
            throw new StudentNotEnrolledException(studentId, gradeDto.courseId());
        }

        Grade grade = gradeMapper.toEntity(gradeDto);
        grade.setStudent(student);
        grade.setCourse(course);

        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    @Transactional
    public void deleteGradeById(Long id){
        if (!gradeRepository.existsById(id)) {
            throw new ResourceNotFoundException(Grade.class, id);
        }
        gradeRepository.deleteById(id);
    }

    public List<ResponseGradeDto> getAllGradesByStudentId(Long studentId){
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(gradeMapper::toDto)
                .toList();
    }

    public ResponseGradeDto updateGradeById(Long id, UpdateGradeDto gradeUpdates) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Grade.class, id));

        gradeMapper.updateEntity(gradeUpdates, grade);
        grade = gradeRepository.save(grade);

        return gradeMapper.toDto(grade);
    }
}
