package com.example.springboot101.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot101.dto.AppointmentDTO;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.SlotDTO;
import com.example.springboot101.handler.ResourceNotFoundException;
import com.example.springboot101.services.AppointmentService;
import com.example.springboot101.services.DoctorService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors/{doctorId}/slots")
    public ResponseEntity<List<SlotDTO>> getSlots(@PathVariable Long doctorId) {
        DoctorDTO doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found with id " + doctorId);
        }
        List<SlotDTO> slots = appointmentService.getAvailableSlots(doctorId);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO bookedAppointment = appointmentService.bookAppointment(appointmentDTO);
        return ResponseEntity.ok(bookedAppointment);
    }

    @GetMapping("/appointments/patient/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable Long userId) {
        // PatientDTO doctor = patientService.getPatien(userId);
        // if (doctor == null) {
        //     throw new ResourceNotFoundException("Patient not found with id " + userId);
        // }
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }
}
