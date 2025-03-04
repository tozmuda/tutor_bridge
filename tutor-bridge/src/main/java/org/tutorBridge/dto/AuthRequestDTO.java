package org.tutorBridge.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password cannot be empty")
        String password) {
}
