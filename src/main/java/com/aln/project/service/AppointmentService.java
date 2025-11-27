package com.aln.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aln.project.exceptions.NotFoundException;
import com.aln.project.model.Appointment;
import com.aln.project.model.Client;
import com.aln.project.model.ServiceItem;
import com.aln.project.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepo;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ServiceItemService serviceItem;

	public Appointment create(Appointment appointment) {
	    Client client = clientService.findById(appointment.getClient().getId());
	    appointment.setClient(client);

	    ServiceItem item = serviceItem.findById(appointment.getService().getId());
	    appointment.setService(item);

	    return appointmentRepo.save(appointment);
	}

	public List<Appointment> findAll() {
		return appointmentRepo.findAll();
	}

	public Appointment findById(Long id) {
		return appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException("Agendamento não encontrado!!"));
	}

	public Appointment update(Long id, Appointment updated) {
		Appointment existing = findById(id);

		existing.setDateTime(updated.getDateTime());
		existing.setStatus(updated.getStatus());
		existing.setPaymentMethod(updated.getPaymentMethod());
		existing.setTotalPrice(updated.getTotalPrice());
		existing.setClient(updated.getClient());
		existing.setService(updated.getService());

		return appointmentRepo.save(existing);
	}

	public void delete(Long id) {
		Appointment existing = findById(id);
		appointmentRepo.delete(existing);
	}
}
