package com.abeer.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Validated 
@Component 
@ConfigurationProperties (prefix = "app.jwt")
@Getter 
@Setter 
public class JwtProperties {
    @NotBlank private String secret;
    @Positive private long expirationMs;
    
}
