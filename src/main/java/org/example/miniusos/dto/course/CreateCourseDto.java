package org.example.miniusos.dto.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCourseDto(
        @NotBlank(message = "Course name cannot be empty")
        String name,

        @NotNull(message = "Course must have ECTS points")
        @Min(value = 1, message = "Course must have at least 1 ECTS point")
        Integer ectsPoints
) {}
