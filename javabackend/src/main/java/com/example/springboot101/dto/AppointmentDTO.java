package com.example.springboot101.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, LocalDate date, LocalTime time, Long doctorId, String doctorName, Long patientId, String patientName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
    }

    
}
