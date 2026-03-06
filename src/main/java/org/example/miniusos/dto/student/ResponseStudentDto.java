package org.example.miniusos.dto.student;

import org.example.miniusos.dto.enrollment.ResponseEnrollmentDto;

import java.util.List;

public record ResponseStudentDto(
        Long id,
        String firstName,
        String lastName,
        String indexNumber,
        List<ResponseEnrollmentDto> enrollments
) {}
