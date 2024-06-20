package com.example.backend.controller;

import com.example.backend.model.cinema.Ticket;
import com.example.backend.payload.request.CreateTicketRequest;
import com.example.backend.payload.request.TicketRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TicketControllerTests {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Test
    void testGetTicket() {
        openMocks(this);

        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);

        when(ticketService.getById(ticketId)).thenReturn(ticket);

        Ticket result = ticketController.getTicket(ticketId);

        assertEquals(ticketId, result.getId());
        verify(ticketService, times(1)).getById(ticketId);
    }

    @Test
    void testSaveTicket() {
        openMocks(this);

        CreateTicketRequest createTicketRequest = new CreateTicketRequest(1L, 5, 10);
        doNothing().when(ticketService).saveTicket(any(CreateTicketRequest.class));

        ResponseEntity<MessageResponse> responseEntity = ticketController.saveTicket(createTicketRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("The ticket has been purchased.", responseEntity.getBody().getMessage());
        verify(ticketService, times(1)).saveTicket(any(CreateTicketRequest.class));
    }

    @Test
    void testClipTicket_Success() {
        openMocks(this);

        TicketRequest ticketRequest = new TicketRequest(1L);
        when(ticketService.clipTicket(any(TicketRequest.class))).thenReturn(true);

        ResponseEntity<MessageResponse> responseEntity = ticketController.clipTicket(ticketRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("The ticket with ID: 1 has been clipped.", responseEntity.getBody().getMessage());
        verify(ticketService, times(1)).clipTicket(any(TicketRequest.class));
    }

    @Test
    void testClipTicket_Failure() {
        openMocks(this);

        TicketRequest ticketRequest = new TicketRequest(1L);
        when(ticketService.clipTicket(any(TicketRequest.class))).thenReturn(false);

        ResponseEntity<MessageResponse> responseEntity = ticketController.clipTicket(ticketRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Error: The ticket with ID: 1 cannot be clipped!", responseEntity.getBody().getMessage());
        verify(ticketService, times(1)).clipTicket(any(TicketRequest.class));
    }
}
