package org.example.miniusos.service;

import org.example.miniusos.dto.course.ResponseCourseDto;
import org.example.miniusos.dto.student.CreateStudentDto;
import org.example.miniusos.dto.student.ResponseStudentDto;
import org.example.miniusos.dto.student.UpdateStudentDto;
import org.example.miniusos.exception.DuplicateResourceException;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.exception.StudentAlreadyEnrolledException;
import org.example.miniusos.mappers.CourseMapper;
import org.example.miniusos.mappers.StudentMapper;
import org.example.miniusos.model.Course;
import org.example.miniusos.model.Enrollment;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.CourseRepository;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, StudentMapper studentMapper, CourseMapper courseMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
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

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, courseId));

        if (student.getEnrollments().stream().map(Enrollment::getCourse).anyMatch(course::equals)) {
            throw new StudentAlreadyEnrolledException(studentId, courseId);
        }

        student.enrollInCourse(course);
    }

    @Transactional
    public void unenrollStudentFromCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, courseId));

        student.unenrollInCourse(course);
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

    public List<ResponseCourseDto> getAllCoursesOfStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));

        return student.getEnrollments().stream()
                .map(Enrollment::getCourse)
                .map(courseMapper::toDto)
                .toList();
    }
}
