package com.example.backend.payload.request;
// Definicja rekordu który przyjmuje trzy parametry: id seansu, numer rzędu i numer miejsca
// id nie puste, i wartości dodatnie
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTicketRequest(@NotNull Long screeningId, @Positive Integer rowNumber, @Positive Integer seatNumber) {
}
