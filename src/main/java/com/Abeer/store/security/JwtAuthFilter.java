package com.abeer.store.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.JwtException;
import java.lang.IllegalArgumentException;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
@Component 
@RequiredArgsConstructor 

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final @Lazy CustomUserDetailsService customUserDetailsService;
    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            try{
                jwtToken = authHeader.substring(7);
                username = jwtService.extractUsername(jwtToken);
                if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if(jwtService.validateToken(jwtToken, userDetails)){
                  UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                
            }
        }
        catch(JwtException | IllegalArgumentException e){

            }

        }
        filterChain.doFilter(request, response);
       
    }
}
