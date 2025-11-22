package com.aln.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.aln.project.model.Appointment;
import com.aln.project.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

   
    @PostMapping("/create")
    public Appointment create(@RequestBody Appointment appointment) {
        return appointmentService.create(appointment);
    }

  
    @GetMapping("/list")
    public List<Appointment> list() {
        return appointmentService.findAll();
    }


    @GetMapping("/{id}")
    public Appointment findById(@PathVariable Long id) {
        return appointmentService.findById(id);
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
}
