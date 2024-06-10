package com.example.backend.service;

import com.example.backend.model.cinema.Movie;
import com.example.backend.model.cinema.Screening;
import com.example.backend.model.cinema.Ticket;
import com.example.backend.model.cinema.TicketStatus;
import com.example.backend.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    // usun mocki
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testTicketValidity16MinsBeforeStartTime() {
        // Utwórz bilet, który zostanie sprawdzony 16 minut przed rozpoczęciem seansu
        Ticket ticket = createTicketBeforeStartTime(16);

        boolean isValid = ticketService.checkValidIfNotClipped(ticket);

        assertFalse(isValid); // Sprawdzam czy nieważny
        assertEquals(TicketStatus.INVALID, ticket.getStatus());// sprawdzenie czy status invalid
    }

    @Test
    public void testTicketValidity15MinsBeforeStartTime() {
        // Utwórz bilet, który zostanie sprawdzony 15 minut przed rozpoczęciem seansu
        Ticket ticket = createTicketBeforeStartTime(15);
        ticket.setStatus(TicketStatus.INVALID);

        boolean isValid = ticketService.checkValidIfNotClipped(ticket);

        assertTrue(isValid);// Sprawdzam czy ważny
        assertEquals(TicketStatus.VALID, ticket.getStatus()); // Sprawdzam czy status jest Valid
    }

    @Test
    public void testTicketValidityExactly30MinsAfterEndTime() {
        // Utwórz bilet, 30 minut po zakończeniu seansu
        Ticket ticket = createTicketAfterEndTime(30);

        boolean isValid = ticketService.checkValidIfNotClipped(ticket);

        assertFalse(isValid); // Sprawdzam czy nieważny
        assertEquals(TicketStatus.INVALID, ticket.getStatus()); // Sprawdzam czy status jest Valid
    }

    @Test
    public void testTicketValidityExactly1MinBeforeAfterEndTime() {
        // Utowórz bilet 1 minutę przed zakończeniem seansu
        Ticket ticket = createTicketAfterEndTime(-1);

        boolean isValid = ticketService.checkValidIfNotClipped(ticket);

        assertTrue(isValid); // Sprawdzam czy ważny
        assertEquals(TicketStatus.VALID, ticket.getStatus()); // Sprawdzam czy status jest Valid
    }

    @Test
    public void testTicketValidityExactly0MinBeforeAfterEndTime() {
        // Utwórz bilet w chwili zakończenia seansu
        Ticket ticket = createTicketAfterEndTime(0);

        boolean isValid = ticketService.checkValidIfNotClipped(ticket);

        assertFalse(isValid); // Sprawdzam czy nieważny
        assertEquals(TicketStatus.INVALID, ticket.getStatus()); // Sprawdzam czy status jest INVALID
    }
    // Bilet przed rozpoczęciem seansu
    private Ticket createTicketBeforeStartTime(int minutesBeforeStartTime) {
        LocalDateTime screeningStartTime = LocalDateTime.now().plusMinutes(minutesBeforeStartTime);
        Movie movie = new Movie();
        movie.setLengthInMins(120);
        Screening screening = new Screening();
        screening.setDateOfBeginning(screeningStartTime);
        screening.setMovie(movie);
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.INVALID);
        ticket.setScreening(screening);

        return ticket;
    }
    // Bilet po zakończeniu seansu
    private Ticket createTicketAfterEndTime(int minutesAfterStartTime) {
        LocalDateTime screeningEndTime = LocalDateTime.now().minusMinutes(minutesAfterStartTime);
        LocalDateTime screeningStartTime = screeningEndTime.minusMinutes(120);
        Movie movie = new Movie();
        movie.setLengthInMins(120);
        Screening screening = new Screening();
        screening.setDateOfBeginning(screeningStartTime);
        screening.setMovie(movie);
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.INVALID);
        ticket.setScreening(screening);
        return ticket;
    }
}