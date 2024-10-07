package com.example.springboot101.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot101.dto.AuthenticationRequest;
import com.example.springboot101.dto.AuthenticationResponse;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.PatientDTO;
import com.example.springboot101.dto.UserDTO;
import com.example.springboot101.models.City;
import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.models.Specialization;
import com.example.springboot101.security.JwtUtil;
import com.example.springboot101.services.CustomUserDetailsService;
import com.example.springboot101.services.DoctorService;
import com.example.springboot101.services.PatientService;
import com.example.springboot101.util.constants.Roles;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.error("Incorrect username or password");
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        String role = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .orElse("");
        
        UserDTO userDTO = customUserDetailsService.fetchUserByUsername(userDetails.getUsername(), role);
        userDTO.setJwt(jwt);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register_doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.registerDoctor(doctorDTO);
        return ResponseEntity.ok("Doctor registered");
    }

    @PostMapping("/register_patient")
    public ResponseEntity<String> registerPatientPost(@RequestBody PatientDTO patientDTO) {
        patientService.registerPatient(patientDTO);
        return ResponseEntity.ok("Patient registered");
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<String> isAuthenticated() {
        return ResponseEntity.ok("isAuthenticated");
    }
}
