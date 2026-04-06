package com.zenshaf01.redditwebflux.service;

import com.zenshaf01.redditwebflux.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private String expiration;

    /**
     * Generates a JWT token for the account
     * @param account user account
     * @return a token
     */
    public String generateToken(@NonNull Account account) {
        return Jwts.builder()
            .subject(account.getUsername())
            .claim("id", account.getId())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
            .signWith(getSigningKey())
            .compact();
    }

    /**
     * Get signing key from the secret
     * @return Secret key
     */
    public SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Checks if the token is valid
     * @param token JWT token from incoming request
     * @return true or false
     */
    public Boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
