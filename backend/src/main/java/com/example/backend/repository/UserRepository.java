package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Wyszukanie użytkonika po mailu (moze nie znaleśc)
    Optional<User> findByEmail(String email);
    // Sprawdzenie czy istnieje użytkownik
    Boolean existsByEmail(String email);
}
