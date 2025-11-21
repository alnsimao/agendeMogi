package com.aln.project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aln.project.exceptions.ConflictException;
import com.aln.project.exceptions.CpfDuplicateException;
import com.aln.project.exceptions.CpfInvalidException;
import com.aln.project.exceptions.EmailDuplicateException;
import com.aln.project.exceptions.NotFoundException;
import com.aln.project.model.Client;
import com.aln.project.repository.ClientRepository;

@Service
public class ClientService {

	private boolean IsCpfValid(String cpf) {
		if (cpf == null) {
			return false;
		}

		cpf = cpf.trim();

		if (cpf.length() != 11) {
			return false;
		}

		if (!cpf.matches("\\d+")) {
			return false;
		}
		return true;
	}
	
	private void validateCpfDuplicate(String cpf, Long currentId) {

		if (cpf == null || cpf.isBlank()) {
			return;
		}

		Client client = clientRepo.findByCpf(cpf).orElse(null);

		if (client != null && !client.getId().equals(currentId)) {
			throw new ConflictException("CPF já cadastrado");
		}
	}

	private void validateEmailDuplicate(String email, Long currentId) {
		if (email == null || email.isBlank()) {
			return;
		}
		Client client = clientRepo.findByEmail(email).orElse(null);

		if (client != null && !client.getEmail().equals(currentId)) {
			throw new ConflictException("Email já cadastrado");
		}
	}

	@Autowired
	ClientRepository clientRepo;

	public Client createClient(Client client) {
		if (!IsCpfValid(client.getCpf())) {
			throw new CpfInvalidException("CPF inválido");
		}
		if (clientRepo.findByCpf(client.getCpf()).isPresent()) {
			throw new CpfDuplicateException("CPF já cadastrado!!!");
		}
		if (clientRepo.findByEmail(client.getEmail()).isPresent()) {
			throw new EmailDuplicateException("Email já cadastrado!!!");
		}
		return clientRepo.save(client);

	}

	public Client editClient(Long id, Client updated) {
		Client saved = findById(id);
		
		validateCpfDuplicate(updated.getCpf(), id);
		validateEmailDuplicate(updated.getEmail(), id);
		
		saved.setName(updated.getName());
		saved.setCpf(updated.getCpf());
		saved.setEmail(updated.getEmail());
		saved.setNumber(updated.getNumber());
		
		return clientRepo.save(saved);
		
	}

	public Client findById(Long id) {
		System.out.println("Buscando ID no banco: " + id);
		System.out.println("Existe? " + clientRepo.findById(id).isPresent());

		return clientRepo.findById(id)

				.orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
	}

	public void deleteClient(Long id) {
		Client client = findById(id);
		
		clientRepo.delete(client);
		
	}



}
