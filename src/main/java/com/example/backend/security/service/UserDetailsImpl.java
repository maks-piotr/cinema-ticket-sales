package com.example.backend.security.service;

import com.example.backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
// Spring Seciurity, adpter usera
// Poptrzbe do autoryzacji, wzbogaca model usera o informacje do SpringSecuirty

public class UserDetailsImpl implements UserDetails {
    // kontorla nad serlializacja
    // zapis stanu obiektu z bit i na obiekt
    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String email;

    @JsonIgnore
    private final String password;

    private final String firstName;

    private final String lastName;
    // lista uprawnein
    private final Collection<? extends GrantedAuthority> authorities;
    // Kontruktor który przymuje inforacje do stworzzenia UserDetails
    public UserDetailsImpl(Long id, String email, String password, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }
    // Metoda budujaca UserDetailImp na podstawie User
    public static UserDetailsImpl build(User user) {
        // przetowrzenie ról użytkownika na obiekt GrantedAuthority i zebranie ich do listy
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());


        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // Pórwnaanie na podstaiwi id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsImpl that)) return false;
        return Objects.equals(id, that.id);
    }
}