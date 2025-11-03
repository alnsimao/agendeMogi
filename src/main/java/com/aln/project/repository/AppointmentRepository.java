package com.aln.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aln.project.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
