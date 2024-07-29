package com.example.springboot101.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private DoctorService accountService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void run(String... args) throws Exception {

        Specialization specialization01 = new Specialization();
        specialization01.setName("ENT");

        Specialization specialization02 = new Specialization();
        specialization02.setName("Dentist");
        specializationService.save(specialization01);
        specializationService.save(specialization02);

        City city01 = new City();
        city01.setName("delhi");
        City city02 = new City();
        city02.setName("mumbai");
        cityService.save(city01);
        cityService.save(city02);

        Doctor account01 = new Doctor();
        Doctor account02 = new Doctor();
        Doctor account03 = new Doctor();
        Doctor account04 = new Doctor();

        account01.setEmail("abc@gmail.com");
        account01.setPassword("pass");
        account01.setName("yash");
        account01.setSpecialization(specialization01);
        account01.setCity(city01);
        account01.setRole(Roles.DOCTOR.getRole());

        account02.setEmail("naman@gmail.com");
        account02.setPassword("pass");
        account02.setName("naman");
        account02.setSpecialization(specialization01);
        account02.setCity(city01);
        account02.setRole(Roles.DOCTOR.getRole());


        account03.setEmail("shubh@gmail.com");
        account03.setPassword("pass");
        account03.setName("shubh");
        account03.setSpecialization(specialization02);
        account03.setCity(city02);
        account03.setRole(Roles.DOCTOR.getRole());



        account04.setEmail("ajit@gmail.com");
        account04.setPassword("pass");
        account04.setName("ajit");
        account04.setSpecialization(specialization02);
        account04.setCity(city02);
        account04.setRole(Roles.DOCTOR.getRole());


        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);
        

        Patient patient01 = new Patient();
        Patient patient02 = new Patient();
        Patient patient03 = new Patient();
        Patient patient04 = new Patient();

        patient01.setEmail("vaibhav@gmail.com");
        patient01.setPassword("pas");
        patient01.setName("vaibhav");
        patient01.setRole(Roles.PATIENT.getRole());

        patient02.setEmail("prat@gmail.com");
        patient02.setPassword("pass");
        patient02.setName("prat");
        patient02.setRole(Roles.PATIENT.getRole());

        patient03.setEmail("ktk@gmail.com");
        patient03.setPassword("pass");
        patient03.setName("ktk");
        patient03.setRole(Roles.PATIENT.getRole());


        patient04.setEmail("tanishk@gmail.com");
        patient04.setPassword("pass");
        patient04.setName("tanishk");
        patient04.setRole(Roles.PATIENT.getRole());

        patientService.save(patient01);
        patientService.save(patient02);
        patientService.save(patient03);
        patientService.save(patient04);


        // Appointment appointment01 = new Appointment();

        // appointment01.setDoctor(account01);
        // appointment01.setPatient(patient01);
        // appointmentService.save(appointment01);

        // Appointment appointment02 = new Appointment();

        // appointment02.setDoctor(account02);
        // appointment02.setPatient(patient02);
        // appointmentService.save(appointment02);
        
        
    }
    
}
