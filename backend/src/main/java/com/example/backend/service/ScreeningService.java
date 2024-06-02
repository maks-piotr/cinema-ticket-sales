package com.example.backend.service;

import com.example.backend.model.cinema.Screening;
import com.example.backend.repository.ScreeningRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningService(ScreeningRepository screening) {
        this.screeningRepository = screening;
    }

    public Screening getById(long id) {
        return screeningRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Screening with ID " + id + " not found"));
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}
