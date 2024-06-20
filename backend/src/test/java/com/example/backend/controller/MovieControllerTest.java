package com.example.backend.controller;

import com.example.backend.model.cinema.Movie;
import com.example.backend.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    public MovieControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMovie() {
        Long movieId = 1L;
        Movie movie = new Movie();
        when(movieService.getById(movieId)).thenReturn(movie);

        Movie result = movieController.getMovie(movieId);
        assertEquals(movie, result);
    }

    @Test
    public void testGetAllMovies() {
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieService.getAllMovies()).thenReturn(movies);

        List<Movie> result = movieController.getAllMovies();
        assertEquals(movies, result);
    }
}
