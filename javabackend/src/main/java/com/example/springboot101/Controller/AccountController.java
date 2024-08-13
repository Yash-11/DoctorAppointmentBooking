package com.example.springboot101.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.PatientDTO;
import com.example.springboot101.models.City;
import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.models.Specialization;
import com.example.springboot101.services.DoctorService;
import com.example.springboot101.services.PatientService;
import com.example.springboot101.util.constants.Roles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/register_doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDTO doctorDTO) {

        Specialization specialization = new Specialization();
        specialization.setName(doctorDTO.getSpecializationName());

        City city = new City();
        city.setName(doctorDTO.getCityName());

        Doctor doctor = new Doctor();
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setName(doctorDTO.getName());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setRole(Roles.DOCTOR.getRole());
        doctor.setSpecialization(specialization);
        doctor.setCity(city);
        doctorService.save(doctor);
        return ResponseEntity.ok("register_patient");
    }

    @PostMapping("/register_patient")
    public ResponseEntity<String> registerPatientPost(@RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setAge(patientDTO.getAge());
        patient.setEmail(patientDTO.getEmail());
        patient.setName(patientDTO.getName());
        patient.setRole(Roles.PATIENT.getRole());
        patient.setPassword(patientDTO.getPassword());
        patientService.save(patient);
        return ResponseEntity.ok("redirect:/");
    }
}
