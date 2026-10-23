package com.abeer.store.entity;

import java.util.List;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user;
    public CustomUserDetails(User user){
        this.user=user;
    }
    //standard spring security method
    @Override 
    public String getUsername(){
        return user.getUsername();
    }
    @Override 
    public String getPassword(){
        return user.getPassword();
    }
    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
    }
    //cutome fields 
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
}
