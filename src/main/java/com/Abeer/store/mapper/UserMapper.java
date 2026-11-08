package com.abeer.store.mapper;

import org.springframework.stereotype.Component;
import com.abeer.store.entity.User;
import com.abeer.store.dto.user.UserResponse;;
@Component 
public class UserMapper {
    public UserResponse toResponse(User user){
        return new UserResponse(user.getId(), user.getUsername(),user.getRole());
    }
}
