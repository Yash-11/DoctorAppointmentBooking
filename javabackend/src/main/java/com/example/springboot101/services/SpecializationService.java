package com.example.springboot101.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot101.dto.SpecializationDTO;
import com.example.springboot101.models.City;
import com.example.springboot101.models.Specialization;
import com.example.springboot101.repositories.SpecializationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepository;

    public Specialization save(Specialization specialization){
        log.info("Specialization : "+specialization.getName()+" added");
        return specializationRepository.save(specialization);
    }

    public Specialization addOrGet(String name) {
        Optional<Specialization> opSpecialization = specializationRepository.findOneByName(name);
        if (opSpecialization.isPresent()) {
            return opSpecialization.get();
        } 

        Specialization specialization = new Specialization();
        specialization.setName(name);
        return save(specialization);
    }

    public Optional<Specialization> findById (Long id) {
        return specializationRepository.findById(id);
    }

    public List<SpecializationDTO> findAll() {
        List<Specialization> specializations = specializationRepository.findAll();
        return specializations.stream()
            .map(specialization -> new SpecializationDTO(specialization.getId(), specialization.getName()))
            .collect(Collectors.toList());
    }

    public List<Specialization> findByName(String name) {
        return specializationRepository.findByName(name);
    }

}
