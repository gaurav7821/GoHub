package com.gohub.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(){

        String secret = "my-super-secret-key-for-gohub-jwt-authentication";

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    public String generateToken(String email){

        return Jwts.builder()
                .subject(email)
                .issuedAt(new java.util.Date())
                .expiration(
                        new java.util.Date(
                                System.currentTimeMillis() + 1000 * 60 * 60
                        )
                )
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token,
                                org.springframework.security.core.userdetails.UserDetails userDetails){

        String email = extractEmail(token);

        return email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){

        return extractExpiration(token)
                .before(new Date());
    }

    private Date extractExpiration(String token){

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

}
