package org.example.miniusos.exception;

public class StudentAlreadyEnrolledException extends RuntimeException {
    public StudentAlreadyEnrolledException(Long studentId, Long courseId) {
        super(String.format("Student with id %d is already enrolled in course with id %d", studentId, courseId));
    }
}
