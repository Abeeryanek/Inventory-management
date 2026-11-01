package com.abeer.store.service;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import com.abeer.store.entity.CustomUserDetails;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.lang.IllegalArgumentException;
import com.abeer.store.config.JwtProperties;
@RequiredArgsConstructor 
@Service 
public class JwtService {
    private final JwtProperties jwtProperties;
    private SecretKey key;
    @PostConstruct 
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getExpirationMs()))
                .signWith(key)
                .compact();
    }
//time=time now + 60second*60minutes*10minutes -> token availabe for 10 minutes.
    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }
    public boolean isValid(String token, CustomUserDetails userDetails){
        try{
            return parseClaims(token).getSubject().equals(userDetails.getUsername());
        }
        catch(JwtException | IllegalArgumentException e){
            return false;
        }
    }
    private Claims parseClaims(String token){
        return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token).getPayload();
    }
}
