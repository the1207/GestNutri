package com.gestNutri.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final long DUREE_VALIDITE_MS = 1000L * 60 * 60 * 10;
    private final SecretKey cleSecrete;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET doit contenir au moins 32 caracteres");
        }
        cleSecrete = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String genererToken(UserDetails userDetails) {
        Date maintenant = new Date();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(maintenant)
                .expiration(new Date(maintenant.getTime() + DUREE_VALIDITE_MS))
                .signWith(cleSecrete)
                .compact();
    }

    public String extraireEmail(String token) {
        return Jwts.parser().verifyWith(cleSecrete).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean estValide(String token) {
        try {
            extraireEmail(token);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
