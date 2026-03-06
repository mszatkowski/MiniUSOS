package org.example.miniusos.dto.course;

import jakarta.validation.constraints.Min;

public record UpdateCourseDto(
        String name,

        @Min(value = 1, message = "Course must have at least 1 ECTS point")
        Integer ectsPoints
) {}
