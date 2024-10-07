package com.example.springboot101.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot101.dto.AppointmentDTO;
import com.example.springboot101.dto.SlotDTO;
import com.example.springboot101.models.Appointment;
import com.example.springboot101.repositories.AppointmentRepository;
import com.example.springboot101.repositories.DoctorRepository;
import com.example.springboot101.repositories.PatientRepository;
import com.example.springboot101.util.constants.AppointmentState;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    
    public Appointment save(Appointment appointment) {
        if (appointment.getId()==null) {
            appointment.setDate(LocalDate.now());   
            appointment.setTime(LocalTime.of(10, 0));   
        }

        log.info("Appointment added : "+appointment.getTime()+" "+appointment.getDate()+" "+ appointment.getDoctor().getName());
        log.info("Appointment doctor : "+ appointment.getDoctor().getName());
        log.info("Appointment patient : "+ appointment.getPatient().getName());
        return appointmentRepository.save(appointment);
    }

    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        return appointments.stream()
                .map(appointment -> new AppointmentDTO(
                        appointment.getId(),
                        appointment.getDate(),
                        appointment.getTime(),
                        appointment.getDoctor().getId(),
                        appointment.getDoctor().getName(),
                        appointment.getPatient().getId(),
                        appointment.getPatient().getName()))
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsByUserId(Long userId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(userId);
        return appointments.stream().map(appointment -> new AppointmentDTO(
            appointment.getId(),
            appointment.getDate(),
            appointment.getTime(),
            appointment.getDoctor().getId(),
            appointment.getDoctor().getName(),
            appointment.getPatient().getId(),
            appointment.getPatient().getName()
        )).collect(Collectors.toList());
    }

    public List<SlotDTO> getAvailableSlots(Long doctorId) {
        List<SlotDTO> availableSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 4; i++) {
            LocalDate date = today.plusDays(i);
            List<LocalTime> slots = getAvailableSlotsOnDate(doctorId, date);
            SlotDTO slotDTO = new SlotDTO(date, slots);
            availableSlots.add(slotDTO);
        }

        return availableSlots;
    }

    public List<LocalTime> getAvailableSlotsOnDate(Long doctorId, LocalDate date) {
        List<LocalTime> allSlots = generateTimeSlots(LocalTime.of(10, 0), LocalTime.of(18, 0), 60);
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDate(doctorId, date);

        List<LocalTime> bookedSlots = appointments.stream()
                .map(appointment -> appointment.getTime())
                .collect(Collectors.toList());

        allSlots.removeAll(bookedSlots);

        return allSlots;
    }

    public List<LocalTime> generateTimeSlots(LocalTime start, LocalTime end, int intervalMinutes) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime slot = start;
 
        while (slot.isBefore(end)) {
            slots.add(slot);
            slot = slot.plusMinutes(intervalMinutes);
        }
        return slots;
    }

    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO, String username) {
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setDoctor(doctorRepository.findById(appointmentDTO.getDoctorId()).orElseThrow());
        appointment.setPatient(patientRepository.findOneByEmailIgnoreCase(username).get());
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return new AppointmentDTO(
        savedAppointment.getId(),
        savedAppointment.getDate(),
        savedAppointment.getTime(),
        savedAppointment.getDoctor().getId(),
        savedAppointment.getDoctor().getName(),
        savedAppointment.getPatient().getId(),
        savedAppointment.getPatient().getName());
    }

    public AppointmentDTO confirmAppointment(Long appointmentid) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentid);

        Appointment appointment = optionalAppointment.get();
        appointment.setAppointmentState(AppointmentState.BOOKED);
        return new AppointmentDTO(
        appointment.getId(),
        appointment.getDate(),
        appointment.getTime(),
        appointment.getDoctor().getId(),
        appointment.getDoctor().getName(),
        appointment.getPatient().getId(),
        appointment.getPatient().getName());
    
    }

    public void cancelAppointment(Long appointmentid) {
        appointmentRepository.deleteById(appointmentid);
    }
}