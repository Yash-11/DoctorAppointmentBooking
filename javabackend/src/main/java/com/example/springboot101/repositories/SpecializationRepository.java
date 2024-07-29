package com.example.springboot101.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot101.models.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
