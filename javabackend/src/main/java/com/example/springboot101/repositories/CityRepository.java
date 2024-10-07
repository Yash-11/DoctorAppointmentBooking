package com.example.springboot101.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot101.models.City;
import com.example.springboot101.models.Doctor;

import java.util.List;
import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByName(String name);

    Optional<City> findOneByName(String name);
}
