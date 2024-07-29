package com.example.springboot101.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.repositories.DoctorRepository;
import com.example.springboot101.repositories.PatientRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Long fetchUserIdByUsername(String email, String role) {
        if ("ROLE_DOCTOR".equals(role)) {
            return doctorRepository.findOneByEmailIgnoreCase(email).get().getId();
        } else {
            return patientRepository.findOneByEmailIgnoreCase(email).get().getId();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] parts = username.split(":");
        String email = parts[0];
        String role = parts[1];

        if ("DOCTOR".equals(role)) {

            Optional<Doctor> optionaldoctor = doctorRepository.findOneByEmailIgnoreCase(email);
            if (optionaldoctor.isPresent()) {
                Doctor doctor = optionaldoctor.get();
                return new org.springframework.security.core.userdetails.User(
                    doctor.getEmail(),
                    doctor.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR"))
                );
            }
            throw new UsernameNotFoundException("User not found");
        } else if ("PATIENT".equals(role)) {

            Optional<Patient> optionalpatient = patientRepository.findOneByEmailIgnoreCase(email);
    
            if (optionalpatient.isPresent()) {
                Patient patient = optionalpatient.get();
                return new org.springframework.security.core.userdetails.User(
                    patient.getEmail(),
                    patient.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"))
                );
            }
            throw new UsernameNotFoundException("User not found");
        } else {
            throw new UsernameNotFoundException("Invalid role: " + role);
        }
                
    }




}
