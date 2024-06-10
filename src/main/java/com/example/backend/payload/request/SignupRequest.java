package com.example.backend.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @Size(max = 50) @Email String email,
        @Size(min = 6, max = 40) String password,
        @Size(min = 3, max = 20) String firstName,
        @Size(min = 3, max = 20) String lastName) {
}
