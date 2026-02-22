package org.example.miniusos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record StudentDto (
        Long id,

        @NotBlank(message = "First name cannot be empty")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        String lastName,

        @Pattern(regexp = "^\\d{6}$", message = "Index number must be 6 digits")
        String indexNumber){
}
