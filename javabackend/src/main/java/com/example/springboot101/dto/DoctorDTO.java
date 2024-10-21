package com.example.springboot101.dto;

import java.util.List;

import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;
    private String name;
    private List<String> specializations;  // List of specializations names
    private String cityName;
    private int yearsOfExperience;
    private String address;
    private int rating;
    private String description;
    private List<String> services;  // List of services provided by the doctor
    private List<String> educations;  // List of education details
    private List<String> experiences; // List of work experiences
    private String email;
    private String password;

    // Constructor
    public DoctorDTO(Long id, String name, List<String> specializations, String cityName,
                     int yearsOfExperience, String address, int rating,
                     String description, List<String> services, List<String> educations, 
                     List<String> experiences) {
        this.id = id;
        this.name = name;
        this.specializations = specializations;  // List of specializations
        this.cityName = cityName;
        this.yearsOfExperience = yearsOfExperience;
        this.address = address;
        this.rating = rating;
        this.description = description;
        this.services = services;
        this.educations = educations;
        this.experiences = experiences;
    }
}

