package com.aln.project.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aln.project.model.Appointment;
import com.aln.project.repository.AppointmentRepository;
import com.aln.project.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private AppointmentRepository appointmentRepository;

   
    @PostMapping("/create")
    public Appointment create(@RequestBody Appointment appointment) {
        return appointmentService.create(appointment);
    }

  
    @GetMapping("/list")
    public List<Appointment> list() {
        return appointmentService.findAll();
    }




    @PutMapping("/edit/{id}")
    public Appointment editAppointment(@PathVariable Long id, @RequestBody Appointment updated) {
        return appointmentService.update(id, updated);
    }

   
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return "Agendamento removido com sucesso!";
    }
    @GetMapping("/complete/{clientId}")
    public List<Appointment> findByClientId(@PathVariable Long clientId) {
    	   return appointmentRepository.findByClientId(clientId);
    }
}	
