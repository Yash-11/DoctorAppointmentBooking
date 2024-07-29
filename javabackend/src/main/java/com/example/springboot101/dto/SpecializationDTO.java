package com.example.springboot101.dto;

import lombok.Data;

@Data
public class SpecializationDTO {
    private Long id;
    private String name;

    public SpecializationDTO() {}

    public SpecializationDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
