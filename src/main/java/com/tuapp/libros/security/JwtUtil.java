package com.tuapp.libros.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta (deberías moverla a application.properties)
    private final String SECRET_KEY = "Mi_Clave_Super_Secreta_Para_JWT_Debe_Ser_Larga_Y_Segura_123456";
    
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    // Duración del token: 24 horas
    private final long EXPIRATION_TIME = 86400000; // 24 * 60 * 60 * 1000

    // Generar token
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Extraer email del token
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extraer todas las claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validar token
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Verificar si expiró
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
