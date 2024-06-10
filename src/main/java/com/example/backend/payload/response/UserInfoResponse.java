package com.example.backend.payload.response;

import java.util.List;

public record UserInfoResponse(Long id, String email, String firstName, String lastName, List<String> roles) {
}
