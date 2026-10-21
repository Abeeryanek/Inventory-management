package com.abeer.store.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
    
    @Bean 
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //customizer<CsrfConfigure<HttpSecurity>> csrf=new customizer <CsrfConfigure<HttpSecurity>>;

         http
         .csrf(csrf->csrf.disable())
         .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .authorizeHttpRequests(request->request.anyRequest().authenticated())
        .build();
        
        
        return http.build();
       
    }
  
    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
}


