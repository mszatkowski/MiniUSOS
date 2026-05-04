package org.example.miniusos.service;

import org.example.miniusos.dto.grade.*;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.exception.StudentNotEnrolledException;
import org.example.miniusos.mappers.GradeMapper;
import org.example.miniusos.model.CourseDetail;
import org.example.miniusos.model.Enrollment;
import org.example.miniusos.model.Grade;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.CourseDetailRepository;
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
    private final CourseDetailRepository courseDetailRepository;
    private final GradeMapper gradeMapper;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, CourseDetailRepository courseDetailRepository, GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseDetailRepository = courseDetailRepository;
        this.gradeMapper = gradeMapper;
    }

    @Transactional
    public ResponseGradeDto addGrade(Long studentId, CreateGradeDto gradeDto){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));
        CourseDetail courseDetail = courseDetailRepository.findById(gradeDto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(CourseDetail.class, gradeDto.courseId()));

        boolean isEnrolled = student.getEnrollments().stream()
                .map(Enrollment::getCourseDetail)
                .anyMatch(courseDetail::equals);

        if (!isEnrolled) {
            throw new StudentNotEnrolledException(studentId, gradeDto.courseId());
        }

        Grade grade = gradeMapper.toEntity(gradeDto);
        grade.setStudent(student);
        grade.setCourseDetail(courseDetail);

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
