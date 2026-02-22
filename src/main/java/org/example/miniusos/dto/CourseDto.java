package org.example.miniusos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CourseDto(
        Long id,

        @NotBlank(message = "Course name cannot be empty")
        String name,

        @Min(value = 1, message = "Course must have at least 1 ECTS point")
        int ectsPoints) {
}
