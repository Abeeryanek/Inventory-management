package com.abeer.store.controller;

import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.mapper.UserMapper;
import com.abeer.store.security.JwtService;
import com.abeer.store.service.UserService;

import lombok.RequiredArgsConstructor;

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
public abstract class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    //TODO api register 
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request){
        
        UserResponse response;
        return ResponseEntity.ok(null);
    }
    //TODO api login 
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody String entity) {
        AuthResponse response;
        
        return ResponseEntity.ok(null);
    }
    
    
}









