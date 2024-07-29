package com.example.springboot101.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class SlotDTO {
    private LocalDate date;
    private List<LocalTime> availableTimes;

    // Constructor
    public SlotDTO(LocalDate date, List<LocalTime> availableTimes) {
        this.date = date;
        this.availableTimes = availableTimes;
    }
}
