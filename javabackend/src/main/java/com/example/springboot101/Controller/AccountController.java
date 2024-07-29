package com.example.springboot101.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.springboot101.dto.AppointmentDTO;
import com.example.springboot101.dto.CityDTO;
import com.example.springboot101.dto.DoctorDTO;
import com.example.springboot101.dto.PatientDTO;
import com.example.springboot101.dto.SlotDTO;
import com.example.springboot101.dto.SpecializationDTO;
import com.example.springboot101.handler.ResourceNotFoundException;
import com.example.springboot101.models.Appointment;
import com.example.springboot101.models.City;
import com.example.springboot101.models.Doctor;
import com.example.springboot101.models.Patient;
import com.example.springboot101.models.Specialization;
import com.example.springboot101.services.AppointmentService;
import com.example.springboot101.services.CityService;
import com.example.springboot101.services.DoctorService;
import com.example.springboot101.services.PatientService;
import com.example.springboot101.services.SpecializationService;
import com.example.springboot101.util.constants.Roles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AppointmentService appointmentService;

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

    @GetMapping("/all_specializations")
    public ResponseEntity<List<SpecializationDTO>> getSpecializations() {
        List<SpecializationDTO> specializations = specializationService.findAll();
        return ResponseEntity.ok(specializations);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDTO>> getCities() {
        List<CityDTO> cities = cityService.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> queryDoctors(@RequestParam String specialization, @RequestParam String city) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecializationAndCity(specialization, city);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long doctorId) {
        DoctorDTO doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found with id " + doctorId);
        }
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("/doctors/{doctorId}/slots")
    public ResponseEntity<List<SlotDTO>> getSlots(@PathVariable Long doctorId) {
        DoctorDTO doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found with id " + doctorId);
        }
        List<SlotDTO> slots = appointmentService.getAvailableSlots(doctorId);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO bookedAppointment = appointmentService.bookAppointment(appointmentDTO);
        return ResponseEntity.ok(bookedAppointment);
    }

    @GetMapping("/appointments/patient/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable Long userId) {
        // PatientDTO doctor = patientService.getPatien(userId);
        // if (doctor == null) {
        //     throw new ResourceNotFoundException("Patient not found with id " + userId);
        // }
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }
}
