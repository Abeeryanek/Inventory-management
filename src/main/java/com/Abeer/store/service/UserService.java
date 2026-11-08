package com.abeer.store.service;

import org.springframework.stereotype.Service;
import com.abeer.store.repository.UserRepository;


import lombok.RequiredArgsConstructor;


import com.abeer.store.dto.user.UserResponse;

import com.abeer.store.mapper.UserMapper;



@RequiredArgsConstructor
@Service 

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    //TODO create custome exceptions
    public UserResponse findMe(Long id){
        return userRepository.findById(id).map(userMapper::toResponse).orElseThrow(()->  new IllegalArgumentException("User not found with id: " + id));
        
    }

    

}
