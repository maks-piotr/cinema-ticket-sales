package com.example.backend.controller;

import com.example.backend.model.cinema.CinemaHall;
import com.example.backend.service.CinemaHallService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CinemaHallControllerTest {

    @Mock
    private CinemaHallService cinemaHallService;

    @InjectMocks
    private CinemaHallController cinemaHallController;

    public CinemaHallControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCinemaHall() {
        Long cinemaHallId = 1L;
        CinemaHall cinemaHall = new CinemaHall();
        when(cinemaHallService.getById(cinemaHallId)).thenReturn(cinemaHall);

        CinemaHall result = cinemaHallController.getCinemaHall(cinemaHallId);
        assertEquals(cinemaHall, result);
    }

    @Test
    public void testGetAllCinemaHalls() {
        CinemaHall hall1 = new CinemaHall();
        CinemaHall hall2 = new CinemaHall();
        List<CinemaHall> halls = Arrays.asList(hall1, hall2);
        when(cinemaHallService.getAllCinemaHalls()).thenReturn(halls);

        List<CinemaHall> result = cinemaHallController.getAllCinemaHalls();
        assertEquals(halls, result);
    }
}
