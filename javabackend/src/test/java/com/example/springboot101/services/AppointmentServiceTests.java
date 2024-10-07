package com.example.springboot101.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springboot101.repositories.AppointmentRepository;

public class AppointmentServiceTests {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGenerateTimeSlots(){
        LocalTime start = LocalTime.of(9, 0);  
        LocalTime end = LocalTime.of(11, 0); 
        int intervalMinutes = 30;

        List<LocalTime> expectedSlots = List.of(
            LocalTime.of(9, 0),
            LocalTime.of(9, 30),
            LocalTime.of(10, 0),
            LocalTime.of(10, 30)
        );

        List<LocalTime> actualSlots = appointmentService.generateTimeSlots(start, end, intervalMinutes);

        assertEquals(expectedSlots, actualSlots);
    }
}
