package org.example.miniusos.service;

import org.example.miniusos.dto.course.ResponseCourseDetailDto;
import org.example.miniusos.dto.student.CreateStudentDto;
import org.example.miniusos.dto.student.ResponseStudentDto;
import org.example.miniusos.dto.student.UpdateStudentDto;
import org.example.miniusos.exception.DuplicateResourceException;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.exception.StudentAlreadyEnrolledException;
import org.example.miniusos.mappers.CourseDetailMapper;
import org.example.miniusos.mappers.StudentMapper;
import org.example.miniusos.model.CourseDetail;
import org.example.miniusos.model.Enrollment;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.CourseDetailRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseDetailRepository courseDetailRepository;
    private final StudentMapper studentMapper;
    private final CourseDetailMapper courseDetailMapper;

    public StudentService(StudentRepository studentRepository, CourseDetailRepository courseDetailRepository, StudentMapper studentMapper, CourseDetailMapper courseDetailMapper) {
        this.studentRepository = studentRepository;
        this.courseDetailRepository = courseDetailRepository;
        this.studentMapper = studentMapper;
        this.courseDetailMapper = courseDetailMapper;
    }

    @Transactional
    public ResponseStudentDto addStudent(CreateStudentDto studentDto){
        if (studentRepository.existsByIndexNumber(studentDto.indexNumber())) {
            throw new DuplicateResourceException(Student.class, "index number",studentDto.indexNumber());
        }

        Student student = studentMapper.toEntity(studentDto);
        student = studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    public List<ResponseStudentDto> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));

        CourseDetail courseDetail = courseDetailRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseDetail.class, courseId));

        if (student.getEnrollments().stream().map(Enrollment::getCourseDetail).anyMatch(courseDetail::equals)) {
            throw new StudentAlreadyEnrolledException(studentId, courseId);
        }

        student.enrollInCourse(courseDetail);
        studentRepository.save(student);
    }

    @Transactional
    public void unenrollStudentFromCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));

        CourseDetail courseDetail = courseDetailRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(CourseDetail.class, courseId));

        student.unenrollInCourse(courseDetail);
    }

    public ResponseStudentDto getStudentById(Long id){
        return studentMapper.toDto(studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, id)));
    }

    @Transactional
    public void deleteStudentById(Long id){
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException(Student.class, id);
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public ResponseStudentDto updateStudentById(Long id, UpdateStudentDto studentUpdates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, id));

        studentMapper.updateEntity(studentUpdates, student);
        student = studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    public List<ResponseCourseDetailDto> getAllCoursesOfStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));

        return student.getEnrollments().stream()
                .map(Enrollment::getCourseDetail)
                .map(courseDetailMapper::toDto)
                .toList();
    }
}
