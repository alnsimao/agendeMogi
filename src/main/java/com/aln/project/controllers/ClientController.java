package com.aln.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aln.project.model.Client;
import com.aln.project.repository.ClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController{
	
	@Autowired
	private ClientRepository clientRepo;
	
	@PostMapping("/")
	public ResponseEntity<Client> newClient(@RequestBody Client client){
		Client savedClient = clientRepo.save(client);
		return ResponseEntity.ok(savedClient);
	}
	@GetMapping("/list")
	public List<Client> findAllClient(){
		List<Client> clients = clientRepo.findAll();
		return clients;
	}
	

}
