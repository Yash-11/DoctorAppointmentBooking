package com.example.springboot101.config;

import java.util.List;

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

    // Utility method to handle Specialization creation if not present
    private Specialization getOrCreateSpecialization(String name) {
        List<Specialization> specializationList = specializationService.findByName(name);
        if (specializationList.isEmpty()) {
            Specialization specialization = new Specialization();
            specialization.setName(name);
            return specializationService.save(specialization); // return the saved object
        }
        return specializationList.get(0);
    }

    // Utility method to handle City creation if not present
    private City getOrCreateCity(String name) {
        List<City> cityList = cityService.findByName(name);
        if (cityList.isEmpty()) {
            City city = new City();
            city.setName(name);
            return cityService.save(city); // return the saved object
        }
        return cityList.get(0);
    }

    // Utility method to handle Doctor creation
    private void createDoctorIfNotExists(String email, String name, Specialization specialization, City city) {
        if (accountService.findOneByEmail(email).isEmpty()) {
            Doctor doctor = new Doctor();
            doctor.setEmail(email);
            doctor.setPassword("pass");
            doctor.setName(name);
            doctor.setSpecialization(specialization);
            doctor.setCity(city);
            doctor.setRole(Roles.DOCTOR.getRole());
            accountService.save(doctor);
        }
    }

    // Utility method to handle Patient creation
    private void createPatientIfNotExists(String email, String name) {
        if (patientService.findOneByEmail(email).isEmpty()) {
            Patient patient = new Patient();
            patient.setEmail(email);
            patient.setPassword("pass");
            patient.setName(name);
            patient.setRole(Roles.PATIENT.getRole());
            patientService.save(patient);
        }
    }

    // Main seeding logic
    // public void seedDatabase() {
    // Specialization entSpecialization = getOrCreateSpecialization("ENT");
    // Specialization dentistSpecialization = getOrCreateSpecialization("Dentist");

    // City delhiCity = getOrCreateCity("delhi");
    // City mumbaiCity = getOrCreateCity("mumbai");

    // // Create Doctors
    // createDoctorIfNotExists("abc@gmail.com", "yash", entSpecialization,
    // delhiCity);
    // createDoctorIfNotExists("naman@gmail.com", "naman", entSpecialization,
    // delhiCity);
    // createDoctorIfNotExists("shubh@gmail.com", "shubh", dentistSpecialization,
    // mumbaiCity);
    // createDoctorIfNotExists("ajit@gmail.com", "ajit", dentistSpecialization,
    // mumbaiCity);

    // // Create Patients
    // createPatientIfNotExists("vaibhav@gmail.com", "vaibhav");
    // createPatientIfNotExists("prat@gmail.com", "prat");
    // createPatientIfNotExists("ktk@gmail.com", "ktk");
    // createPatientIfNotExists("tanishk@gmail.com", "tanishk");
    // }

    @Override
    public void run(String... args) throws Exception {

        Specialization entSpecialization = getOrCreateSpecialization("ENT");
        Specialization dentistSpecialization = getOrCreateSpecialization("Dentist");

        City delhiCity = getOrCreateCity("Delhi");
        City mumbaiCity = getOrCreateCity("Mumbai");

        // Create Doctors
        createDoctorIfNotExists("abc@gmail.com", "Yash", entSpecialization, delhiCity);
        createDoctorIfNotExists("naman@gmail.com", "Naman", entSpecialization, delhiCity);
        createDoctorIfNotExists("shubh@gmail.com", "Shubh", dentistSpecialization, mumbaiCity);
        createDoctorIfNotExists("ajit@gmail.com", "Ajit", dentistSpecialization, mumbaiCity);

        // Create Patients
        createPatientIfNotExists("vaibhav@gmail.com", "Vaibhav");
        createPatientIfNotExists("prat@gmail.com", "Pratyush");
        createPatientIfNotExists("ktk@gmail.com", "Kartikey");
        createPatientIfNotExists("tanishk@gmail.com", "Tanishk");

        // Specialization specialization01;
        // List<Specialization> specializationList =
        // specializationService.findByName("ENT");
        // if (specializationList.isEmpty()) {
        // specialization01 = new Specialization();
        // specialization01.setName("ENT");
        // specializationService.save(specialization01);
        // } else {
        // specialization01 = specializationList.get(0);
        // }

        // if (specializationService.findByName("Dentist").isEmpty()) {
        // Specialization specialization02 = new Specialization();
        // specialization02.setName("Dentist");
        // specializationService.save(specialization02);
        // }

        // if (cityService.findByName("delhi").isEmpty()) {
        // City city01 = new City();
        // city01.setName("delhi");
        // cityService.save(city01);
        // }

        // if (cityService.findByName("mumbai").isEmpty()) {
        // City city02 = new City();
        // city02.setName("mumbai");
        // cityService.save(city02);
        // }

        // Doctor account01 = new Doctor();
        // Doctor account02 = new Doctor();
        // Doctor account03 = new Doctor();
        // Doctor account04 = new Doctor();

        // account01.setEmail("abc@gmail.com");
        // account01.setPassword("pass");
        // account01.setName("yash");
        // account01.setSpecialization(specialization01);
        // account01.setCity(city01);
        // account01.setRole(Roles.DOCTOR.getRole());

        // account02.setEmail("naman@gmail.com");
        // account02.setPassword("pass");
        // account02.setName("naman");
        // account02.setSpecialization(specialization01);
        // account02.setCity(city01);
        // account02.setRole(Roles.DOCTOR.getRole());

        // account03.setEmail("shubh@gmail.com");
        // account03.setPassword("pass");
        // account03.setName("shubh");
        // account03.setSpecialization(specialization02);
        // account03.setCity(city02);
        // account03.setRole(Roles.DOCTOR.getRole());

        // account04.setEmail("ajit@gmail.com");
        // account04.setPassword("pass");
        // account04.setName("ajit");
        // account04.setSpecialization(specialization02);
        // account04.setCity(city02);
        // account04.setRole(Roles.DOCTOR.getRole());

        // accountService.save(account01);
        // accountService.save(account02);
        // accountService.save(account03);
        // accountService.save(account04);

        // Patient patient01 = new Patient();
        // Patient patient02 = new Patient();
        // Patient patient03 = new Patient();
        // Patient patient04 = new Patient();

        // patient01.setEmail("vaibhav@gmail.com");
        // patient01.setPassword("pas");
        // patient01.setName("vaibhav");
        // patient01.setRole(Roles.PATIENT.getRole());

        // patient02.setEmail("prat@gmail.com");
        // patient02.setPassword("pass");
        // patient02.setName("prat");
        // patient02.setRole(Roles.PATIENT.getRole());

        // patient03.setEmail("ktk@gmail.com");
        // patient03.setPassword("pass");
        // patient03.setName("ktk");
        // patient03.setRole(Roles.PATIENT.getRole());

        // patient04.setEmail("tanishk@gmail.com");
        // patient04.setPassword("pass");
        // patient04.setName("tanishk");
        // patient04.setRole(Roles.PATIENT.getRole());

        // patientService.save(patient01);
        // patientService.save(patient02);
        // patientService.save(patient03);
        // patientService.save(patient04);

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
