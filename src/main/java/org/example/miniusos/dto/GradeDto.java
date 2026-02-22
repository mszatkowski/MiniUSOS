package org.example.miniusos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record GradeDto(
        Long id,

        @Min(value = 0, message = "Score cannot be negative")
        @Max(value = 100, message = "Score cannot be greater than 100")
        int score,

        @NotBlank(message = "Subject name cannot be empty")
        String subjectName) {
}
