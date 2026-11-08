package com.abeer.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abeer.store.dto.user.UserResponse;
import com.abeer.store.security.CustomUserDetails;
import com.abeer.store.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/api/users") 
public class UserController {
    
    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser (@AuthenticationPrincipal CustomUserDetails userDetail) {
       UserResponse response=userService.findMe(userDetail.getId());
       return ResponseEntity.ok(response);
    }


}



