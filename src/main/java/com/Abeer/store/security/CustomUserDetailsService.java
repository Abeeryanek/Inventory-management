package com.abeer.store.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abeer.store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.abeer.store.entity.User;
@Service 
@RequiredArgsConstructor 
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            
        // Return your custom wrapper holding the entity data
        return new CustomUserDetails(user); 

    }
}
