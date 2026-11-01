package com.abeer.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration 
@ConfigurationProperties (prefix = "app.jwt")
@Getter 
@Setter 
public class JwtProperties {
    private String secret;
    private long expirationMs;
    
}
