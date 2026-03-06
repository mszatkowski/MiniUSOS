package org.example.miniusos.dto.grade;

public record ResponseGradeDto(
        Long id,
        Long courseId,
        String courseName,
        Integer score,
        String description
) {}
