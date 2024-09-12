package com.example.springboot101.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot101.dto.CityDTO;
import com.example.springboot101.models.City;
import com.example.springboot101.repositories.CityRepository;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City save(City City){
        return cityRepository.save(City);

    }

    public Optional<City> findById (Long id) {
        return cityRepository.findById(id);
    }

    public List<CityDTO> findAll() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
            .map(city -> new CityDTO(city.getId(), city.getName()))
            .collect(Collectors.toList());
    }

    public List<City> findByName(String name) {
        return cityRepository.findByName(name);
    }
    
}
