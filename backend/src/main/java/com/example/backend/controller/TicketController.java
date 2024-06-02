package com.example.backend.controller;

import com.example.backend.model.cinema.Ticket;
import com.example.backend.payload.request.CreateTicketRequest;
import com.example.backend.payload.request.TicketRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
// Kontrola biletów sprawdzanie stanu i innych danych
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    // Endpoint do pobierania danych biletu na podstawie ID tylko TICKET_COLLECTOR
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TICKET_COLLECTOR')")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getById(id);
    }
    // Endpoint do kupienia biletu, każdy
    @PostMapping("/buy")
    public ResponseEntity<MessageResponse> saveTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest) {
        ticketService.saveTicket(createTicketRequest);

        return ResponseEntity.ok().body(new MessageResponse("The ticket has been purchased."));
    }
    // Endpoint do kasowania biletów, tylko TICKET_COLLECTOR
    @PatchMapping("/clip")
    @PreAuthorize("hasRole('TICKET_COLLECTOR')")
    public ResponseEntity<MessageResponse> clipTicket(@Valid @RequestBody TicketRequest clipTicketRequest) {
        if(ticketService.clipTicket(clipTicketRequest)) {
            return ResponseEntity.ok().body(new MessageResponse("The ticket with ID: " + clipTicketRequest.ticketId() + " has been clipped."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: The ticket with ID: " + clipTicketRequest.ticketId() + " cannot be clipped!"));
        }
    }
}
