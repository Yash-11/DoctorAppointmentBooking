package com.example.springboot101.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findOneByEmailIgnoreCase(String email);

    List<Doctor> findBySpecializationNameAndCityName(String specializationName, String cityName);

    DoctorDTO getDoctorById(Long doctorId);

}
