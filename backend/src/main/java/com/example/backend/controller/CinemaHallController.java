package com.example.backend.controller;

import com.example.backend.model.cinema.CinemaHall;
import com.example.backend.service.CinemaHallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// przetwarzanie żądań z innych źródeł
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/cinema-halls")
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    @Autowired
    public CinemaHallController(CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }
    // Zwraca sale po id
    @GetMapping("/{id}")
    public CinemaHall getCinemaHall(@PathVariable Long id) {
        return cinemaHallService.getById(id);
    }
    // Zwraca wszytkie sale
    @GetMapping("")
    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallService.getAllCinemaHalls();
    }

}
