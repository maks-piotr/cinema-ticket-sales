package com.example.backend.controller;

import com.example.backend.payload.request.LoginRequest;
import com.example.backend.payload.request.SignupRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.security.jwt.JwtUtils;
import com.example.backend.security.service.UserDetailsImpl;
import com.example.backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthControllerTests {

    @Mock
    private AuthService authService;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AuthController authController;

    @InjectMocks
    private AuthService authServiceImpl;

    @Test
    void authenticateUserInServiceTest() {
        openMocks(this);

        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        authServiceImpl = new AuthService(authenticationManager, null, null, encoder);
        UserDetailsImpl result = authServiceImpl.authenticateUser(loginRequest);

        assertEquals(userDetails, result);
    }

    @Test
    void registerUserTest() {
        openMocks(this);

        SignupRequest signupRequest = new SignupRequest("test@example.com", "password123", "John", "Doe");

        when(authService.registerUser(any(SignupRequest.class))).thenReturn(true);

        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered successfully!", ((MessageResponse) Objects.requireNonNull(responseEntity.getBody())).getMessage());
    }

}
