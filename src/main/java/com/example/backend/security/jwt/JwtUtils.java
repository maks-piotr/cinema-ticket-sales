package com.example.backend.security.jwt;

import com.example.backend.model.User;
import com.example.backend.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
// Zarządzania tokenami JWT i ich powiązania z ciasteczkami HTTP
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    // Pobieranie ciasto JWT
    @Value("${cinema.app.jwtCookieName}")
    private String jwtCookie;
    // Sekretny klucz do podpisywania JWT, pobierany z konfiguracji aplikacji.
    @Value("${cinema.app.jwtSecret}")
    private String jwtSecret;
    // Czas wygaśnięcia tokenu JWT
    @Value("${cinema.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    // Generuje ciasteczko JWT na podstawie danych użytkownika (UserDetailsImpl)
    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        return generateCookie(jwtCookie, jwt, "/api");
    }
    // Generuje ciasteczko JWT na podstawie obiektu User
    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateTokenFromUsername(user.getEmail());
        return generateCookie(jwtCookie, jwt, "/api");
    }
    // Tworzy ciasteczko JWT z pustą wartością czyli wylogowuje
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }
    // Pobiera nazwe uzytkownika z tokenu
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
    // Generowanie klucza do podpisywania JWT na podstawie sekretu
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        }

        return false;
    }
    // Generowanie tokenu JWT na podstawie nazwy użytkownika
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    // Generowanie ciasteczka HTTP z tokenem JWT
    private ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60 * 7).httpOnly(true).build();
        return cookie;
    }
    // Pobieranie wartośći ciasteczka HTTP
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);

        return cookie != null ? cookie.getValue() : null;
    }
}