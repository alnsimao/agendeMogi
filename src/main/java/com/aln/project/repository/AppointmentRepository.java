package com.aln.project.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aln.project.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	boolean existsByDateTime(LocalDateTime dateTime);
	
	
		List<Appointment> findByClientId(Long clientId);

		

}
