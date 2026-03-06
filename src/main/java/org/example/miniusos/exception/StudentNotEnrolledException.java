package org.example.miniusos.exception;

public class StudentNotEnrolledException extends IllegalArgumentException {
    public StudentNotEnrolledException(Long studentId, Long courseId) {
        super(String.format("Student with id %d is not enrolled in course with id %d", studentId, courseId));
    }
}
