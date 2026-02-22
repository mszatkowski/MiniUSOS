package org.example.miniusos.service;

import org.example.miniusos.dto.StudentDto;
import org.example.miniusos.exception.ResourceNotFoundException;
import org.example.miniusos.model.Student;
import org.example.miniusos.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDto addStudent(StudentDto studentDto){
        return mapToDto(studentRepository.save(mapToEntity(studentDto)));
    }

    public List<StudentDto> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public StudentDto getStudentById(Long id){
        return mapToDto(studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("student", id)));
    }

    public void deleteStudentById(Long id){
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("student", id);
        }
        studentRepository.deleteById(id);
    }

    public StudentDto updateStudentById(Long id, StudentDto studentUpdates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("student", id));

        student.updateFrom(mapToEntity(studentUpdates));
        return mapToDto(studentRepository.save(student));
    }

    private StudentDto mapToDto(Student student) {
        return new StudentDto(student.getId(), student.getFirstName(), student.getLastName(), student.getIndexNumber());
    }

    private Student mapToEntity(StudentDto dto) {
        Student student = new Student();
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setIndexNumber(dto.indexNumber());
        return student;
    }
}
