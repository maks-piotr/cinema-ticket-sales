package com.example.backend.model.cinema;

import jakarta.persistence.*;

@Entity(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private Screening screening;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private Integer seatNumber;

    private Integer rowNumber;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seat) {
        this.seatNumber = seat;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer row) {
        this.rowNumber = row;
    }
}
