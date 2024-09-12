package com.example.springboot101.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.handler.ResourceNotFoundException;
import com.example.springboot101.services.DoctorService;

@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> queryDoctors(@RequestParam String specialization, @RequestParam String city) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecializationAndCity(specialization, city);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long doctorId) {
        DoctorDTO doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found with id " + doctorId);
        }
        return ResponseEntity.ok(doctor);
    }
    
}
