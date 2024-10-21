package com.example.springboot101.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findOneByEmailIgnoreCase(String email);

    @Query("SELECT d FROM Doctor d JOIN d.specializations s WHERE s.name = :specialization AND d.city.name = :city")
    List<Doctor> findBySpecializationAndCity(String specialization, String city);

    DoctorDTO getDoctorById(Long doctorId);

}
