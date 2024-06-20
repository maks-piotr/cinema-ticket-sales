package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
// Reprezentajca Uzytkownika (Biletera) w bazie
@Entity
// tabela users z uniklanym email
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Size(max = 30)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;
    // Relacja wiele do wielu
    // Uzytkownik może byc przypisany do wiele ról i rola może być przypisana do wielu userów
    @ManyToMany(fetch = FetchType.LAZY)
    // tabela pośrednia user_roles która łaczy user i role
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"), // uzytkownik
        inverseJoinColumns = @JoinColumn(name = "role_id")) // rola
    // przechowanie bez duplikatow
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank @Size(max = 50) @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Size(max = 50) @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(max = 120) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(max = 120) String password) {
        this.password = password;
    }

    public @NotBlank @Size(max = 50) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @Size(max = 50) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank @Size(max = 50) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @Size(max = 50) String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
