package org.example.miniusos.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateGradeDto(
        @Min(value = 0, message = "Score cannot be negative")
        @Max(value = 100, message = "Score cannot be greater than 100")
        Integer score,

        String description
) {}
