package com.example.springboot101.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String password;

    // Constructor, getters, and setters

    public UserDTO(Long id, String username, String email, String role, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
