package com.example.backend.service;

import com.example.backend.model.cinema.CinemaHall;
import com.example.backend.repository.CinemaHallRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;

    @Autowired
    public CinemaHallService(CinemaHallRepository cinemaHallRepository) {
        this.cinemaHallRepository = cinemaHallRepository;
    }

    public CinemaHall getById(long id) {
        return cinemaHallRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CinemaHall with ID " + id + " not found"));
    }
}
