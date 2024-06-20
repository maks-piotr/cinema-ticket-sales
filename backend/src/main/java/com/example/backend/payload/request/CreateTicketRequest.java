package com.example.backend.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Dane do utowrzenia nowego biletu
public record CreateTicketRequest(@NotNull Long screeningId, @Positive Integer rowNumber, @Positive Integer seatNumber) {
}
