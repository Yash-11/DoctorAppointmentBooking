package com.example.springboot101.dto;

import lombok.Data;

@Data
public class PatientDTO {

    private String name;
    private int age;
    private String email;
    private String password;

    // Default constructor
    public PatientDTO() {}

    // Parameterized constructor
    public PatientDTO(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
}
