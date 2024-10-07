package com.example.springboot101.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springboot101.dto.UserDTO;
import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.repositories.DoctorRepository;
import com.example.springboot101.repositories.PatientRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public UserDTO fetchUserByUsername(String email, String role) {
        if ("ROLE_DOCTOR".equals(role)) {
            Doctor doctor = doctorRepository.findOneByEmailIgnoreCase(email).get();
            return new UserDTO(doctor.getId(), doctor.getEmail(), doctor.getEmail(), doctor.getRole(), doctor.getPassword());
        } else {
            Patient patient = patientRepository.findOneByEmailIgnoreCase(email).get();
            return new UserDTO(patient.getId(), patient.getEmail(), patient.getEmail(), patient.getRole(), patient.getPassword());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Patient> optionalpatient = patientRepository.findOneByEmailIgnoreCase(username);
        Optional<Doctor> optionaldoctor = doctorRepository.findOneByEmailIgnoreCase(username);

        if (optionalpatient.isPresent()) {
            Patient patient = optionalpatient.get();
            return new User( patient.getEmail(), patient.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        } else if (optionaldoctor.isPresent()) {
            Doctor doctor = optionaldoctor.get();
            return new User( doctor.getEmail(), doctor.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        } else {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }        
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     String[] parts = username.split(":");
    //     String email = parts[0];
    //     String role = parts[1];

    //     if ("DOCTOR".equals(role)) {

    //         Optional<Doctor> optionaldoctor = doctorRepository.findOneByEmailIgnoreCase(email);
    //         if (optionaldoctor.isPresent()) {
    //             Doctor doctor = optionaldoctor.get();
    //             return new org.springframework.security.core.userdetails.User(
    //                 doctor.getEmail(),
    //                 doctor.getPassword(),
    //                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCTOR"))
    //             );
    //         }
    //         throw new UsernameNotFoundException("User not found");
    //     } else if ("PATIENT".equals(role)) {

    //         Optional<Patient> optionalpatient = patientRepository.findOneByEmailIgnoreCase(email);
    
    //         if (optionalpatient.isPresent()) {
    //             Patient patient = optionalpatient.get();
    //             return new org.springframework.security.core.userdetails.User(
    //                 patient.getEmail(),
    //                 patient.getPassword(),
    //                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"))
    //             );
    //         }
    //         throw new UsernameNotFoundException("User not found");
    //     } else {
    //         throw new UsernameNotFoundException("Invalid role: " + role);
    //     }
                
    // }




}
