package com.abeer.store.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abeer.store.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import com.abeer.store.entity.User;
@Service 
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override 
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> found = identifier.contains("@")
        ? userRepository.findByEmail(identifier.trim().toLowerCase())
        : userRepository.findByUsername(identifier);
        User user = found.orElseThrow(() -> new UsernameNotFoundException("User not found"));       
        // Return your custom wrapper holding the entity data
        return new CustomUserDetails(user); 

    }
}
