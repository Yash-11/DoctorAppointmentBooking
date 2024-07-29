package com.example.springboot101.services;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.springboot101.models.Authority;
import com.example.springboot101.repositories.DoctorRepository;
import com.example.springboot101.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.springboot101.util.constants.Roles;

@Service
public class PatientService {
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Patient save(Patient account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return patientRepository.save(account);
    }

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     Optional<Patient> optionalAccount = patientRepository.findOneByEmailIgnoreCase(email);
    //     if (!optionalAccount.isPresent()) {
    //         throw new UsernameNotFoundException("Account not found");

    //     }

    //     Patient account = optionalAccount.get();
        
    //     List<GrantedAuthority> grantedAuthority = new ArrayList<>();
    //     grantedAuthority.add(new SimpleGrantedAuthority("Allow"));
    //     return new User(account.getEmail(), account.getPassword(), grantedAuthority);
    // }

    public Optional<Patient> findOneByEmail(String email) {
        return patientRepository.findOneByEmailIgnoreCase(email);
    }
   
}
