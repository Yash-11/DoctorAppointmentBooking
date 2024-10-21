package com.example.springboot101.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;

    private int yearsOfExperience;   // Years of experience for the doctor
    private String address;          // Address of the clinic/hospital
    private int rating;           // Average rating based on patient reviews
    private String description;      // A short bio or description about the doctor

    // A doctor can have multiple specializations
    @ManyToMany
    @JoinTable(
      name = "doctor_specialization", 
      joinColumns = @JoinColumn(name = "doctor_id"), 
      inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private List<Specialization> specializations;

    @ElementCollection
    private List<String> services;

    @ElementCollection
    private List<String> educations;

    @ElementCollection
    private List<String> experiences;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
