package com.example.springboot101.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.models.Specialization;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.PatientDTO;
import com.example.springboot101.models.Authority;
import com.example.springboot101.models.City;
import com.example.springboot101.repositories.DoctorRepository;
import com.example.springboot101.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.springboot101.util.constants.Roles;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Patient save(Patient account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        log.info("Patient : "+account.getName()+" added");
        return patientRepository.save(account);
    }

    public Patient registerPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setAge(patientDTO.getAge());
        patient.setEmail(patientDTO.getEmail());
        patient.setName(patientDTO.getName());
        patient.setRole(Roles.PATIENT.getRole());
        patient.setPassword(patientDTO.getPassword());
        return save(patient);
    } 

    public Optional<Patient> findOneByEmail(String email) {
        return patientRepository.findOneByEmailIgnoreCase(email);
    }
   
}
