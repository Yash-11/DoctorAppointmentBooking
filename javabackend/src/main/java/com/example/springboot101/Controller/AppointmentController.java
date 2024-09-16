package com.example.springboot101.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot101.dto.AppointmentDTO;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.SlotDTO;
import com.example.springboot101.handler.ResourceNotFoundException;
import com.example.springboot101.models.Appointment;
import com.example.springboot101.models.Patient;
import com.example.springboot101.services.AppointmentService;
import com.example.springboot101.services.DoctorService;
import com.example.springboot101.services.PatientService;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

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
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody AppointmentDTO appointmentDTO, Authentication authentication) {
        String username = authentication.getName();
        AppointmentDTO bookedAppointment = appointmentService.bookAppointment(appointmentDTO, username);
        return ResponseEntity.ok(bookedAppointment);
    }

    @GetMapping("/appointments/patient")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(Authentication authentication) {

        String username = authentication.getName();
        Patient patient = patientService.findOneByEmail(username).get();
        List<Appointment> appointments = patient.getAppointments();

        List<AppointmentDTO> appointmentDTOs = appointments.stream().map(appointment -> new AppointmentDTO(
            appointment.getId(),
            appointment.getDate(),
            appointment.getTime(),
            appointment.getDoctor().getId(),
            appointment.getDoctor().getName(),
            appointment.getPatient().getId(),
            appointment.getPatient().getName()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(appointmentDTOs);
    }

    @GetMapping("/confirmAppointment/{appointmentId}")
    public ResponseEntity<AppointmentDTO> confirmAppointment(@PathVariable Long appointmentId) {
        AppointmentDTO bookedAppointment = appointmentService.confirmAppointment(appointmentId);
        return ResponseEntity.ok(bookedAppointment);
    }

    @GetMapping("/cancelAppointment/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok("cancelled");
    }
}
