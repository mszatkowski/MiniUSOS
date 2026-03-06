package org.example.miniusos.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateStudentDto(
        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @NotBlank(message = "Index number cannot be empty")
        @Pattern(regexp = "^\\d{6}$", message = "Index number must be 6 digits")
        String indexNumber
) {}
