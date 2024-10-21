package com.example.springboot101.config;

import java.util.List;
import java.util.stream.Collectors;

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

    private void createDoctorIfNotExists(String email, String name, List<Specialization> specializations,
            City city, int yearsOfExperience, String address, int rating, String description,
            List<String> services, List<String> education, List<String> experiences) {

        // Check if the doctor already exists based on the email
        if (accountService.findOneByEmail(email).isEmpty()) {

            // Create a new Doctor entity
            Doctor doctor = new Doctor();
            doctor.setEmail(email);
            doctor.setPassword("pass"); // Set a default or secure password
            doctor.setName(name);
            doctor.setSpecializations(specializations); // Set the list of specializations
            doctor.setCity(city);
            doctor.setRole(Roles.DOCTOR.getRole());

            // Set additional fields
            doctor.setYearsOfExperience(yearsOfExperience);
            doctor.setAddress(address);
            doctor.setRating(rating); // Assuming you pass a default or calculated rating
            doctor.setDescription(description);

            // Set services, education, and experiences
            doctor.setServices(services); // Services from the input list
            doctor.setEducations(education); // Education from the input list
            doctor.setExperiences(experiences); // Work experiences from the input list

            // Save the doctor account
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

    @Override
    public void run(String... args) throws Exception {

        // Specializations
        Specialization entSpecialization = getOrCreateSpecialization("ENT");
        Specialization dentistSpecialization = getOrCreateSpecialization("Dentist");
        Specialization cardiologistSpecialization = getOrCreateSpecialization("Cardiologist");
        Specialization dermatologistSpecialization = getOrCreateSpecialization("Dermatologist");
        Specialization neurologistSpecialization = getOrCreateSpecialization("Neurologist");
        Specialization pediatricianSpecialization = getOrCreateSpecialization("Pediatrician");
        Specialization orthopedistSpecialization = getOrCreateSpecialization("Orthopedist");
        Specialization gynecologistSpecialization = getOrCreateSpecialization("Gynecologist");
        Specialization ophthalmologistSpecialization = getOrCreateSpecialization("Ophthalmologist");
        Specialization psychiatristSpecialization = getOrCreateSpecialization("Psychiatrist");

        // Cities
        City delhiCity = getOrCreateCity("Delhi");
        City mumbaiCity = getOrCreateCity("Mumbai");
        City bangaloreCity = getOrCreateCity("Bangalore");
        City kolkataCity = getOrCreateCity("Kolkata");
        City chennaiCity = getOrCreateCity("Chennai");
        City puneCity = getOrCreateCity("Pune");
        City hyderabadCity = getOrCreateCity("Hyderabad");
        City jaipurCity = getOrCreateCity("Jaipur");
        City noidaCity = getOrCreateCity("Noida");
        City gurgaonCity = getOrCreateCity("Gurgaon");

        createDoctorIfNotExists("yash1@gmail.com", "Yash",
                List.of(entSpecialization, dermatologistSpecialization), delhiCity, 5, "Delhi, India", 45,
                "Experienced ENT and Dermatology specialist.",
                List.of("ENT Consultation", "Skin Care Treatments"), List.of("MBBS"),
                List.of("3 years at Delhi Clinic"));

        createDoctorIfNotExists("naman1@gmail.com", "Naman",
                List.of(entSpecialization, pediatricianSpecialization), mumbaiCity, 3, "Mumbai, India", 42,
                "ENT and Pediatric specialist.",
                List.of("Pediatric ENT", "Sinus Surgery"), List.of("MBBS", "MS"),
                List.of("1 year at Mumbai Children's Hospital"));

        createDoctorIfNotExists("shubh1@gmail.com", "Shubh",
                List.of(dentistSpecialization, pediatricianSpecialization), bangaloreCity, 7, "Bangalore, India", 48,
                "Dental and Pediatric care specialist.",
                List.of("Dental Checkups", "Children’s Oral Care"), List.of("BDS"),
                List.of("4 years at Bangalore Dental Care"));

        createDoctorIfNotExists("ajit1@gmail.com", "Ajit",
                List.of(dentistSpecialization, cardiologistSpecialization), kolkataCity, 6, "Kolkata, India", 46,
                "Dental surgeon with a focus on heart-related issues.",
                List.of("Root Canal", "Cardiac Care Consultation"), List.of("BDS", "MDS"),
                List.of("5 years at Kolkata Health Centre"));

        createDoctorIfNotExists("vikas1@gmail.com", "Vikas",
                List.of(cardiologistSpecialization, orthopedistSpecialization), delhiCity, 10, "Delhi, India", 49,
                "Cardiologist with orthopedic expertise.",
                List.of("Cardiac Consultation", "Spinal Care"), List.of("MBBS", "MD"),
                List.of("6 years at Delhi General Hospital"));

        createDoctorIfNotExists("anita1@gmail.com", "Anita",
                List.of(dermatologistSpecialization, ophthalmologistSpecialization), mumbaiCity, 4, "Mumbai, India",
                43,
                "Dermatologist and Eye specialist.",
                List.of("Acne Treatment", "Vision Screening"), List.of("MBBS"),
                List.of("2 years at Mumbai Skin and Eye Clinic"));

        createDoctorIfNotExists("manish1@gmail.com", "Manish",
                List.of(neurologistSpecialization, psychiatristSpecialization), bangaloreCity, 8, "Bangalore, India",
                47,
                "Neurologist and Psychiatrist specializing in brain health.",
                List.of("Epilepsy Consultation", "Therapy for Anxiety"), List.of("MD", "DNB"),
                List.of("7 years at Bangalore Neuro Clinic"));

        createDoctorIfNotExists("neha1@gmail.com", "Neha",
                List.of(pediatricianSpecialization, gynecologistSpecialization), kolkataCity, 6, "Kolkata, India", 46,
                "Pediatric and Women’s Health specialist.",
                List.of("Child Growth Monitoring", "Pregnancy Consultation"), List.of("MBBS", "DCH"),
                List.of("3 years at Kolkata Women's Clinic"));

        createDoctorIfNotExists("rahul1@gmail.com", "Rahul",
                List.of(orthopedistSpecialization, pediatricianSpecialization), delhiCity, 12, "Delhi, India", 48,
                "Orthopedic and Pediatric specialist.",
                List.of("Knee Surgery", "Pediatric Bone Fractures"), List.of("MBBS", "MS"),
                List.of("8 years at Delhi Children's Hospital"));

        createDoctorIfNotExists("kavita1@gmail.com", "Kavita",
                List.of(gynecologistSpecialization, psychiatristSpecialization), mumbaiCity, 9, "Mumbai, India", 45,
                "Gynecologist with a focus on mental health during pregnancy.",
                List.of("Pregnancy Care", "Postpartum Care"), List.of("MBBS", "MD"),
                List.of("5 years at Mumbai Maternity Clinic"));

        createDoctorIfNotExists("sandeep1@gmail.com", "Sandeep",
                List.of(ophthalmologistSpecialization, cardiologistSpecialization), bangaloreCity, 7,
                "Bangalore, India", 46,
                "Eye care and Cardiac specialist.",
                List.of("Cataract Surgery", "ECG"), List.of("MBBS"),
                List.of("6 years at Bangalore Heart and Eye Care"));

        createDoctorIfNotExists("priyanka1@gmail.com", "Priyanka",
                List.of(psychiatristSpecialization, gynecologistSpecialization), kolkataCity, 10, "Kolkata, India", 47,
                "Psychiatrist with specialization in women’s health.",
                List.of("Therapy", "Mental Health in Pregnancy"), List.of("MD"),
                List.of("7 years at Kolkata Women's Clinic"));

        createDoctorIfNotExists("rohit1@gmail.com", "Rohit",
                List.of(entSpecialization, ophthalmologistSpecialization), delhiCity, 4, "Delhi, India", 44,
                "ENT and Eye care specialist.",
                List.of("Hearing Loss Treatment", "Laser Vision Surgery"), List.of("MBBS", "MS"),
                List.of("3 years at Delhi ENT and Eye Hospital"));

        createDoctorIfNotExists("sunita1@gmail.com", "Sunita",
                List.of(dentistSpecialization, pediatricianSpecialization), mumbaiCity, 8, "Mumbai, India", 49,
                "Dental and Pediatric care expert.",
                List.of("Children’s Dentistry", "Dental Fillings"), List.of("BDS", "MDS"),
                List.of("5 years at Mumbai Dental and Pediatric Clinic"));

        createDoctorIfNotExists("anil1@gmail.com", "Anil",
                List.of(neurologistSpecialization, psychiatristSpecialization), delhiCity, 11, "Delhi, India", 47,
                "Experienced Neurologist and Psychiatrist.", List.of("Stroke Care", "Cognitive Behavioral Therapy"),
                List.of(), List.of("10 years as Neurologist at Fortis", "5 years as Psychiatrist at AIIMS"));

        createDoctorIfNotExists("divya1@gmail.com", "Divya",
                List.of(gynecologistSpecialization, dermatologistSpecialization), bangaloreCity, 5, "Bangalore, India",
                44,
                "Women’s Health and Dermatology specialist.", List.of("Prenatal Care", "Skin Rejuvenation"),
                List.of(),
                List.of("3 years as Gynecologist at Apollo", "2 years as Dermatologist at Kaya Skin Clinic"));

        createDoctorIfNotExists("ranjan1@gmail.com", "Ranjan",
                List.of(cardiologistSpecialization, neurologistSpecialization), kolkataCity, 9, "Kolkata, India", 48,
                "Cardiologist with expertise in neurology.", List.of("Heart Health Checkup", "Neurovascular Care"),
                List.of(), List.of("7 years as Cardiologist at AMRI", "4 years as Neurologist at Peerless Hospital"));

        createDoctorIfNotExists("megha1@gmail.com", "Megha",
                List.of(ophthalmologistSpecialization, dentistSpecialization), mumbaiCity, 6, "Mumbai, India", 45,
                "Ophthalmologist and Dental Care specialist.", List.of("Eye Checkup", "Teeth Whitening"),
                List.of(),
                List.of("4 years as Ophthalmologist at Shroff Eye Centre", "2 years as Dentist at Clove Dental"));

        createDoctorIfNotExists("amit1@gmail.com", "Amit",
                List.of(orthopedistSpecialization, pediatricianSpecialization), bangaloreCity, 7, "Bangalore, India",
                46,
                "Orthopedist and Pediatric care expert.", List.of("Joint Replacement", "Pediatric Bone Health"),
                List.of(), List.of("5 years as Orthopedist at Manipal", "2 years as Pediatrician at Rainbow Hospital"));

        createDoctorIfNotExists("seema1@gmail.com", "Seema",
                List.of(gynecologistSpecialization, neurologistSpecialization), kolkataCity, 10, "Kolkata, India", 47,
                "Gynecology and Neurology specialist.", List.of("High-Risk Pregnancy Care", "Migraine Treatment"),
                List.of(),
                List.of("6 years as Gynecologist at Woodlands", "4 years as Neurologist at Belle Vue Clinic"));

        createDoctorIfNotExists("akash1@gmail.com", "Akash",
                List.of(entSpecialization, cardiologistSpecialization), delhiCity, 8, "Delhi, India", 46,
                "ENT and Cardiovascular specialist.", List.of("ENT Surgeries", "Heart Disease Consultation"),
                List.of(), List.of("5 years as ENT at Max Healthcare", "3 years as Cardiologist at Medanta"));

        createDoctorIfNotExists("rekha1@gmail.com", "Rekha",
                List.of(dentistSpecialization, dermatologistSpecialization), mumbaiCity, 9, "Mumbai, India", 48,
                "Dental and Skin Care specialist.", List.of("Cosmetic Dentistry", "Skin Treatments"),
                List.of(),
                List.of("7 years as Dentist at Dr. Batra's", "4 years as Dermatologist at Kaya Skin Clinic"));

        createDoctorIfNotExists("prateek1@gmail.com", "Prateek",
                List.of(psychiatristSpecialization, pediatricianSpecialization), bangaloreCity, 4, "Bangalore, India",
                43,
                "Mental Health and Pediatric specialist.", List.of("Child Psychiatry", "Counseling Sessions"),
                List.of(), List.of("2 years as Psychiatrist at NIMHANS", "2 years as Pediatrician at Cloudnine"));

        createDoctorIfNotExists("nandini1@gmail.com", "Nandini",
                List.of(gynecologistSpecialization, cardiologistSpecialization), kolkataCity, 6, "Kolkata, India", 46,
                "Gynecology and Heart specialist.", List.of("Pregnancy Care", "Cardiac Consultation"),
                List.of(), List.of("4 years as Gynecologist at Apollo", "2 years as Cardiologist at Fortis"));

        createPatientIfNotExists("vaibhav@gmail.com", "Vaibhav");
        createPatientIfNotExists("prat@gmail.com", "Pratyush");

    }

}
