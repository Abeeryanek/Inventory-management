package com.abeer.store.service;


import org.springframework.stereotype.Service;
import com.abeer.store.dto.auth.*;
import com.abeer.store.dto.auth.AuthResponse;
import com.abeer.store.dto.auth.RegisterRequest;

import com.abeer.store.mapper.UserMapper;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthService {
     private final UserMapper userMapper;
    //TODO register service
    public AuthResponse register (RegisterRequest request){
        return null;
    }
    //TODO register login
    public AuthResponse login(){
        return null;
    }
}
