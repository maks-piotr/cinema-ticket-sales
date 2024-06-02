package com.example.backend.model;

import jakarta.persistence.*;
// Encji JPA przypisanie roli dla użytkownika
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // mail ma byc nie pusty i masx 30 znaków
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private RoleEnum name;

    public Role() {}

    public Role(RoleEnum name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
