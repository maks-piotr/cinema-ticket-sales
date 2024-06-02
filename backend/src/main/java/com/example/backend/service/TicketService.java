package com.example.backend.service;

import com.example.backend.model.cinema.Screening;
import com.example.backend.model.cinema.Ticket;
import com.example.backend.model.cinema.TicketStatus;
import com.example.backend.payload.request.CreateTicketRequest;
import com.example.backend.payload.request.TicketRequest;
import com.example.backend.repository.TicketRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ScreeningService screeningService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ScreeningService screeningService) {
        this.ticketRepository = ticketRepository;
        this.screeningService = screeningService;
    }

    public Ticket getById(long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ticket with ID " + id + " not found"));
        checkValidIfNotClipped(ticket);

        return ticket;
    }

    public void saveTicket(CreateTicketRequest createTicketRequest) {
        Screening screening = screeningService.getById(createTicketRequest.screeningId());

        int rowNumber = createTicketRequest.rowNumber();
        int seatNumber = createTicketRequest.seatNumber();

        // Jesli bilet o danym id sali, pozycji siedzenia istnieje to wyrzuca blad.
        Optional<Ticket> occupiedTicketOpt = ticketRepository.findByScreeningAndRowNumberAndSeatNumber(screening, rowNumber, seatNumber);
        if(!occupiedTicketOpt.isEmpty()) {
            throw new EntityExistsException("Ticket with screening ID " + screening.getId() + ", row number " + rowNumber + ", and seat number " + seatNumber + " already exists");
        }

        // Jesli siedzenia nie istnieje w sali to wyrzuca blad.
        if(screening.getCinemaHall().getRows() < rowNumber || screening.getCinemaHall().getSeatsInRows() < seatNumber) {
            throw new IllegalArgumentException("The cinema hall does not have such a seat.");
        }

        Ticket ticket = new Ticket();

        ticket.setScreening(screening);
        ticket.setRowNumber(rowNumber);
        ticket.setSeatNumber(seatNumber);
        ticket.setStatus(TicketStatus.INVALID);

        ticketRepository.save(ticket);
    }

    public boolean checkValidIfNotClipped(Ticket ticket) {
        // Jeśli bilet jest już skasowany, to stan nie ulegnie zmianie
        if (ticket.getStatus() != TicketStatus.CLIPPED) {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime screeningStartTime = ticket.getScreening().getDateOfBeginning();
            LocalDateTime screeningEndTime = screeningStartTime.plusMinutes(ticket.getScreening().getMovie().getLengthInMins());

            if(ticket.getStatus() == TicketStatus.VALID && now.isAfter(screeningEndTime)) {
                ticket.setStatus(TicketStatus.INVALID);
                ticketRepository.save(ticket);
                return false;
            } else if(ticket.getStatus() == TicketStatus.VALID && now.isBefore(screeningEndTime)) {
                // Jesli bilet ma juz status wazny i jest przed koncem seansu to nic nie rob
                return true;
            }


            // Jesli bilet jest przed koncem seansu i jest conajwyzej 15 min przed rozpoczeciem to zmien na wazny
            if (now.isBefore(screeningEndTime) && (now.plusMinutes(15).isEqual(screeningStartTime) || now.plusMinutes(15).isAfter(screeningStartTime))) {
                ticket.setStatus(TicketStatus.VALID);
                ticketRepository.save(ticket);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    // metoda kasujaca bilet
    public boolean clipTicket(TicketRequest clipTicketRequest) {
        Ticket ticket = getById(clipTicketRequest.ticketId());

        if(ticket.getStatus() != TicketStatus.CLIPPED && checkValidIfNotClipped(ticket)) {
            ticket.setStatus(TicketStatus.CLIPPED);
            ticketRepository.save(ticket);
            return true;
        }
        return false;
    }
}
