package com.example.springboot101.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot101.dto.CityDTO;
import com.example.springboot101.dto.SpecializationDTO;
import com.example.springboot101.services.CityService;
import com.example.springboot101.services.SpecializationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MiscController {

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CityService cityService;

    @GetMapping("/")
	public ResponseEntity<?> gethome() {
		return ResponseEntity.ok("Hello World!");
	}
    
    @GetMapping("/all_specializations")
    public ResponseEntity<List<SpecializationDTO>> getSpecializations() {
        log.debug("getting specializations");
        List<SpecializationDTO> specializations = specializationService.findAll();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCities() {
        List<CityDTO> cities = cityService.findAll();
        return ResponseEntity.ok(cities);
    }
}
