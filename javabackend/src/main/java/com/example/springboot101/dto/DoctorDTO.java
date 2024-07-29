package com.example.springboot101.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String specializationName;
    private String cityName;
    private String email;
    private String password;

    public DoctorDTO() {}

    public DoctorDTO(Long id, String name, String specializationName, String cityName) {
        this.id = id;
        this.name = name;
        this.specializationName = specializationName;
        this.cityName = cityName;
    }
}

