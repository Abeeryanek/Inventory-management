package com.abeer.store.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.lang.IllegalArgumentException;
import com.abeer.store.config.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;
@RequiredArgsConstructor 
@Service 
public class JwtService {
    private final JwtProperties jwtProperties;
    private SecretKey key;
    @PostConstruct 
    private void init() {
        System.out.println("secret length = " + jwtProperties.getSecret().length());
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        System.out.println("decoded bytes = " + keyBytes.length);
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
    public boolean validateToken(String token, UserDetails userDetails){
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
