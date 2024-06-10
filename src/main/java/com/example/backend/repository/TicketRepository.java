package com.example.backend.repository;

import com.example.backend.model.cinema.Screening;
import com.example.backend.model.cinema.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Może być puste, wyszukanie biletu po screening, nr rzedu i miejsca
    Optional<Ticket> findByScreeningAndRowNumberAndSeatNumber(Screening screening, Integer rowNumber, Integer seatNumber);
}
