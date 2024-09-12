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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

        // 
        // AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        // authenticationRequest.setUsername("user1");
        // authenticationRequest.setPassword("pass");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

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

    @GetMapping("/register_doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorDTO doctorDTO) {

        Specialization specialization = new Specialization();
        specialization.setName(doctorDTO.getSpecializationName());

        City city = new City();
        city.setName(doctorDTO.getCityName());

        Doctor doctor = new Doctor();
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setName(doctorDTO.getName());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setRole(Roles.DOCTOR.getRole());
        doctor.setSpecialization(specialization);
        doctor.setCity(city);
        doctorService.save(doctor);
        return ResponseEntity.ok("register_patient");
    }

    @PostMapping("/register_patient")
    public ResponseEntity<String> registerPatientPost(@RequestBody PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setAge(patientDTO.getAge());
        patient.setEmail(patientDTO.getEmail());
        patient.setName(patientDTO.getName());
        patient.setRole(Roles.PATIENT.getRole());
        patient.setPassword(patientDTO.getPassword());
        patientService.save(patient);
        return ResponseEntity.ok("redirect:/");
    }
}
