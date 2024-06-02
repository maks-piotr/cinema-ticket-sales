package com.example.backend.payload.request;
// Przyjmuje dwa parametry: email i hasło.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email String email,
        @NotBlank String password) {
}
