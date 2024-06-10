package com.example.backend.service;

import com.example.backend.model.Role;
import com.example.backend.model.RoleEnum;
import com.example.backend.model.User;
import com.example.backend.payload.request.LoginRequest;
import com.example.backend.payload.request.SignupRequest;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    // Autentykacja oparty o token
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // CHaszowanie haseł
    private final PasswordEncoder encoder;
    // Logowanie użytkowników, autentykacja na bazie email i hasła z LoginRequest
    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }
    // Zwraca infrmacje o user
    public UserDetailsImpl authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                // token autentykacji, sprawdany przez AManager
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        // Autentykacja bespeiczenstwa z aktułanymi danymi
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (UserDetailsImpl) authentication.getPrincipal();
    }
    // Rejestracja nowych użytkowników
    public boolean registerUser(SignupRequest signUpRequest) {
        // sprawdza czy istnieje email w bazie danych
        if (userRepository.existsByEmail(signUpRequest.email())) {
            return false;
        }
        // Jesli nie tworzymy nowy obiekt user
        User user = new User(signUpRequest.email(),
                encoder.encode(signUpRequest.password()),
                signUpRequest.firstName(),
                signUpRequest.lastName());

        Set<Role> roles = new HashSet<>();
        // Przypisanie roli
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_TICKET_COLLECTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        // zapisanie user i ustawienie roli
        user.setRoles(roles);
        userRepository.save(user);

        return true;
    }
}