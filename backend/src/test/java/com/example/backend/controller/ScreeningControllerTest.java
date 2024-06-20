package com.example.backend.controller;

import com.example.backend.model.cinema.Screening;
import com.example.backend.service.ScreeningService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreeningControllerTest {

    @Mock
    private ScreeningService screeningService;

    @InjectMocks
    private ScreeningController screeningController;

    public ScreeningControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetScreening() {
        Long screeningId = 1L;
        Screening screening = new Screening();
        when(screeningService.getById(screeningId)).thenReturn(screening);

        Screening result = screeningController.getScreening(screeningId);
        assertEquals(screening, result);
    }

    @Test
    public void testGetAllScreenings() {
        Screening screening1 = new Screening();
        Screening screening2 = new Screening();
        List<Screening> screenings = Arrays.asList(screening1, screening2);
        when(screeningService.getAllScreenings()).thenReturn(screenings);

        List<Screening> result = screeningController.getAllScreenings();
        assertEquals(screenings, result);
    }
}
