package com.example.backend.payload.request;

import jakarta.validation.constraints.NotNull;

public record TicketRequest(@NotNull Long ticketId) {
}