package com.example.backend.model.cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;
// film
@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Relacja jeden do wielu, zdefiniowana przez pole 'movie' w klasie Screening
    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private Set<Screening> screenings;

    private Integer lengthInMins;

    private String director;

    private String title;

    private String description;

    private String photoUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getLengthInMins() {
        return lengthInMins;
    }

    public void setLengthInMins(Integer lengthInMins) {
        this.lengthInMins = lengthInMins;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String image) {
        this.photoUrl = image;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }
}
