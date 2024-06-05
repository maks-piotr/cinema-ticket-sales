package com.example.backend.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTicketRequest(@NotNull Long screeningId, @Positive Integer rowNumber, @Positive Integer seatNumber) {
}
