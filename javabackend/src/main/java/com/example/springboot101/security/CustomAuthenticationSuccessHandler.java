package com.example.springboot101.security;

import com.example.springboot101.dto.UserDTO;
import com.example.springboot101.services.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Fetch user details from the Authentication object
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Assuming you have a method to get the user ID by username
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.stream()
                             .map(GrantedAuthority::getAuthority)
                             .findFirst()
                             .orElse("");

        

        // Create the UserDTO
        UserDTO userDTO = customUserDetailsService.fetchUserByUsername(userDetails.getUsername(), role);

        // Set the response
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(userDTO));
    }

}

