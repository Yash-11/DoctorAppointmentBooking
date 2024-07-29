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
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.models.Authority;
import com.example.springboot101.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.print.Doc;

import com.example.springboot101.util.constants.Roles;

@Service
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Doctor save(Doctor account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        // if (account.getRole()==null)
        //     account.setRole(Roles.USER.getRole());
        return doctorRepository.save(account);
    }

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     Optional<Doctor> optionalAccount = accountRepository.findOneByEmailIgnoreCase(email);
    //     if (!optionalAccount.isPresent()) {
    //         throw new UsernameNotFoundException("Account not found");

    //     }

    //     Doctor account = optionalAccount.get();
        
    //     List<GrantedAuthority> grantedAuthority = new ArrayList<>();
    //     grantedAuthority.add(new SimpleGrantedAuthority("Allow"));
    //     return new User(account.getEmail(), account.getPassword(), grantedAuthority);
    // }

    public Optional<Doctor> findOneByEmail(String email) {
        return doctorRepository.findOneByEmailIgnoreCase(email);
    }

    
    public List<DoctorDTO> getDoctorsBySpecializationAndCity(String specialization, String city) {
        List<Doctor> doctors = doctorRepository.findBySpecializationNameAndCityName(specialization, city);
        return doctors.stream()
            .map(doctor -> new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization().getName(),
                doctor.getCity().getName()))
            .collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long doctorId) {
        Optional<Doctor> op_doctor = doctorRepository.findById(doctorId);
        if (op_doctor.isPresent()) {
            Doctor doctor = op_doctor.get();
            return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization().getName(),
                doctor.getCity().getName());
        } else {
            return null;
        } 
    };

}
