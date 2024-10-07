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
import com.example.springboot101.models.Specialization;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.models.Authority;
import com.example.springboot101.models.City;
import com.example.springboot101.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.print.Doc;

import com.example.springboot101.util.constants.Roles;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Doctor save(Doctor account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        log.info("Doctor : "+account.getName()+" added");
        return doctorRepository.save(account);
    }

    public Doctor registerDoctor(DoctorDTO doctorDTO) {
        Specialization specialization = specializationService.addOrGet(doctorDTO.getSpecializationName());
        City city = cityService.addOrGet(doctorDTO.getCityName());

        Doctor doctor = new Doctor();
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setName(doctorDTO.getName());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setRole(Roles.DOCTOR.getRole());
        doctor.setSpecialization(specialization);
        doctor.setCity(city);
        return save(doctor);
    } 

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
