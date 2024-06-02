package com.example.backend.controller;

import com.example.backend.model.cinema.Screening;
import com.example.backend.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;
    // Konstruktor dla kontrolera z wstrzykiwanym serwisem
    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }
    // Endpoint HTTP GET do pobierania wszystkich seansów filmowych
    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningService.getAllScreenings();
    }
}
