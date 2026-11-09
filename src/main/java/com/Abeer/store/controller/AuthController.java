package com.abeer.store.controller;

import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.mapper.UserMapper;
import com.abeer.store.security.JwtService;
import com.abeer.store.service.AuthService;
import com.abeer.store.service.UserService;

import lombok.RequiredArgsConstructor;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.abeer.store.dto.auth.*;
import com.abeer.store.dto.user.*;


@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor 
public class AuthController {
    
    private final AuthService authService;
    //TODO api register 
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        
        AuthResponse response= authService.register(request);
        // Zeigt dem Client, wo er seine Profildaten abrufen kann
        URI location = URI.create("/api/users/me");
        return ResponseEntity.created(location).body(response);
    }
    //TODO api login 
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response=authService.login(request);
        
        return ResponseEntity.ok(response);
    }
    
    
}









