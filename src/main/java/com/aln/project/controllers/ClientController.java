package com.aln.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aln.project.model.Client;
import com.aln.project.repository.ClientRepository;
import com.aln.project.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController{
	
	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private ClientService clientService;
	
	@PostMapping("/create")
	public Client create(@Valid @RequestBody Client client) {
	    return clientService.createClient(client); 
	}
	@GetMapping("/list")
	public List<Client> findAllClient(){
		return clientRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Client clientId(@PathVariable Long id) {
		return clientService.findById(id);
	}
	
	@PutMapping("/edit/{id}")
	public Client clientEdit (@PathVariable Long id, @RequestBody Client clientUpdated) {
		return clientService.editClient(id, clientUpdated);
	}
	@DeleteMapping("/delete/{id}")
	public void deleteClient(@PathVariable Long id) {
	    clientService.deleteClient(id);
	}
	
	

}
