package com.example.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
// Klasa jest zarządzana przez Spring
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    // rejestracja błedów przez logger
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        int httpStatus = HttpServletResponse.SC_UNAUTHORIZED; // 401

        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Mapper i zapsianie odp
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(authException.getMessage());
        response.getWriter().write(jsonResponse);
    }
}
