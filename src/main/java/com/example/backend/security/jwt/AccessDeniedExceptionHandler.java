package com.example.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
// Wyjątek gdy nie mamy uprawneiń
@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {
    // Logger do logowania informacji
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedExceptionHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // HTTP 403 Forbidden status
        int httpStatus = HttpServletResponse.SC_FORBIDDEN;

        logger.error("Access denied error: {}", accessDeniedException.getMessage());
        // ODP statusu HTTP na 403
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Mapowanie i zapisanie wiadomości
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(accessDeniedException.getMessage());
        response.getWriter().write(jsonResponse);
    }
}
