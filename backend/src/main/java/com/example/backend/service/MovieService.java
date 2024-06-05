package com.example.backend.service;

import com.example.backend.model.cinema.Movie;
import com.example.backend.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getById(long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie with ID " + id + " not found"));
    }
}
