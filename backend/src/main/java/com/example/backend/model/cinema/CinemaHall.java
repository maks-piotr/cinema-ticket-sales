package com.example.backend.model.cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
// sala kinowa
@Entity(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Lista seansów przypisanych do tej sali
    // Relacja jeden do wielu z encją Screening
    @JsonIgnore
    @OneToMany(mappedBy = "cinemaHall")
    private Set<Screening> screenings;

    private String name;

    private Integer rows;

    private Integer seatsInRows;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getSeatsInRows() {
        return seatsInRows;
    }

    public void setSeatsInRows(Integer seatsInRows) {
        this.seatsInRows = seatsInRows;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }
}
