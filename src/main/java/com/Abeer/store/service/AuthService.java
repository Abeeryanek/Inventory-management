package com.abeer.store.service;


import org.springframework.stereotype.Service;
import com.abeer.store.entity.User;
import com.abeer.store.repository.UserRepository;
import com.abeer.store.security.CustomUserDetails;
import com.abeer.store.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import com.abeer.store.dto.auth.AuthResponse;
import com.abeer.store.dto.auth.LoginRequest;
import com.abeer.store.dto.auth.RegisterRequest;


@Service
@RequiredArgsConstructor 
public class AuthService {
     private final  JwtService jwtService;
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final AuthenticationManager authenticationManager;

    public AuthResponse register (RegisterRequest request){
       String email = request.email().toLowerCase();

        if (userRepository.existsByUsername(request.username()))
            throw new IllegalArgumentException("Username already taken");

        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email already registered");

        User user = new User(request.username(), passwordEncoder.encode(request.password()), email);
        userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(new CustomUserDetails(user)));
        }

   public AuthResponse login(LoginRequest request) {
    Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.identifier(), request.password()));

    return new AuthResponse(jwtService.generateToken((CustomUserDetails) auth.getPrincipal()));
}

}
