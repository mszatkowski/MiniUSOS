package org.example.miniusos.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateGradeDto(
        @NotNull(message = "Grade must be assigned to a course")
        Long courseId,

        @NotNull(message = "Grade score")
        @Min(value = 0, message = "Score cannot be negative")
        @Max(value = 100, message = "Score cannot be greater than 100")
        Integer score,

        @NotBlank(message = "Grade description cannot be empty")
        String description
) {}
