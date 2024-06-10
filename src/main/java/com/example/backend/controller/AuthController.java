package com.example.backend.controller;

import com.example.backend.payload.request.LoginRequest;
import com.example.backend.payload.request.SignupRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.payload.response.UserInfoResponse;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.service.UserDetailsImpl;
import com.example.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }
    // POST signin
    @PostMapping("/signin")
    // Sprawdzanie poprawnosci danyc z loginrequest
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // autentykacja + szczegóły
        UserDetailsImpl userDetails = authService.authenticateUser(loginRequest);
        // Zwraca JWR w formie cookie
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        // JWT w nagłówek, dane użytkownika body
        var userInfoResponse = new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getFirstName(), userDetails.getLastName(),
                userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList()));
        // zwracamy ciastko plus dane(body)
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userInfoResponse);
    }
    // POST signup
    @PostMapping("/signup")
    // Sprawdzanie poprawnosci danyc z SignupRequest
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        // Informacje o powdzeniu lub nie rejstracji
        if(authService.registerUser(signupRequest)) {
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: Email is already in use or and wrong data!"));
        }
    }
    // POST singout
    @PostMapping("/signout")
    // Wylogowanie (bileter),
    public ResponseEntity<?> logoutUser() {
        // genrowanie "czystego ciastka" czyli usunięcie sesji biletera
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
