package com.example.springboot101.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot101.models.City;
import com.example.springboot101.models.Specialization;
import java.util.List;
import java.util.Optional;


@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    List<Specialization> findByName(String name);

    Optional<Specialization> findOneByName(String name);
}
