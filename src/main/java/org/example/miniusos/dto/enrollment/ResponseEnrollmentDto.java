package org.example.miniusos.dto.enrollment;

import java.time.LocalDateTime;

public record ResponseEnrollmentDto(
        Long id,
        Long courseId,
        Long studentId,
        String courseName,
        LocalDateTime enrollmentDate
) {}
