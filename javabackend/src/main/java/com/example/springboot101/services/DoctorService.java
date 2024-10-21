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

import lombok.extern.slf4j.Slf4j;@Service
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

    // Save doctor method
    public Doctor save(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        log.info("Doctor : " + doctor.getName() + " added");
        return doctorRepository.save(doctor);
    }

    // Register new doctor
    public Doctor registerDoctor(DoctorDTO doctorDTO) {
        // Get or create specializations (multiple possible specializations)
        List<Specialization> specializations = doctorDTO.getSpecializations().stream()
            .map(specializationService::addOrGet)
            .collect(Collectors.toList());

        // Get or create city
        City city = cityService.addOrGet(doctorDTO.getCityName());

        // Create the doctor entity
        Doctor doctor = new Doctor();
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setName(doctorDTO.getName());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setRole(Roles.DOCTOR.getRole());
        doctor.setSpecializations(specializations);  // Set multiple specializations
        doctor.setCity(city);

        // Set additional attributes
        doctor.setYearsOfExperience(doctorDTO.getYearsOfExperience());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setRating(doctorDTO.getRating());  // Assuming rating starts from 0 or a default value
        doctor.setDescription(doctorDTO.getDescription());

        // Set services and education
        doctor.setServices(doctorDTO.getServices());
        doctor.setEducations(doctorDTO.getEducations());
        doctor.setExperiences(doctorDTO.getExperiences());

        return save(doctor);
    }

    // Find a doctor by email
    public Optional<Doctor> findOneByEmail(String email) {
        return doctorRepository.findOneByEmailIgnoreCase(email);
    }

    // Get doctors by specialization and city
    public List<DoctorDTO> getDoctorsBySpecializationAndCity(String specialization, String city) {
        // return new ArrayList<DoctorDTO>();
        List<Doctor> doctors = doctorRepository.findBySpecializationAndCity(specialization, city);
        return doctors.stream()
            .map(doctor -> new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecializations().stream()
                    .map(Specialization::getName)
                    .collect(Collectors.toList()),  // Collect multiple specializations
                doctor.getCity().getName(),
                doctor.getYearsOfExperience(),
                doctor.getAddress(),
                doctor.getRating(),
                doctor.getDescription(),
                doctor.getServices(),
                doctor.getEducations(),
                doctor.getExperiences()))
            .collect(Collectors.toList());
    }

    // Get doctor details by ID
    public DoctorDTO getDoctorById(Long doctorId) {
        Optional<Doctor> op_doctor = doctorRepository.findById(doctorId);
        if (op_doctor.isPresent()) {
            Doctor doctor = op_doctor.get();
            return new DoctorDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecializations().stream()
                    .map(Specialization::getName)
                    .collect(Collectors.toList()),  // Collect multiple specializations
                doctor.getCity().getName(),
                doctor.getYearsOfExperience(),
                doctor.getAddress(),
                doctor.getRating(),
                doctor.getDescription(),
                doctor.getServices(),
                doctor.getEducations(),
                doctor.getExperiences());
        } else {
            return null;
        }
    }
}
