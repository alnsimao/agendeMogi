package com.aln.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	ClientRepository clientRepo;

	public Client createClient(Client client) {
		if(!IsCpfValid(client.getCpf())
				) {
			throw new RuntimeException("CPF inválido");
		}
		if(clientRepo.findByCpf(client.getCpf()).isPresent()) {
			throw new RuntimeException("CPF já cadastrado!!!");
		}
		return clientRepo.save(client);
		
	}

}
