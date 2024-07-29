package com.example.springboot101.dto;

import lombok.Data;

@Data
public class CityDTO {
    private Long id;
    private String name;

    public CityDTO() {}

    public CityDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}