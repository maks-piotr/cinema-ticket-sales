package com.example.backend.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
public class TestController {
    // Endpont - publiczny dostep
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    // Endpoint z ograniczonym dostępem, tylko dla użytkowników z rolą TICKET_COLLECTOR
    @GetMapping("/ticket-collector")
    @PreAuthorize("hasRole('TICKET_COLLECTOR')")
    public String adminAccess() {
        return "Ticket Collector Content.";
    }
}
