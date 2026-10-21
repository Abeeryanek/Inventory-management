package com.abeer.store.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.abeer.store.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
public class UserController {

    //TODO Service

    @GetMapping("/login")
    public String getMethodName(@RequestParam User user) {
        //TODO: process get login request
        return "ss";
    }
    @PostMapping("/register")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST registration request
        
        return "ss";
    }
    
    
    
    

}
