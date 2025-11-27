package com.aln.project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aln.project.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	

Optional <Client> findByCpf(String cpf);
Optional <Client> findByEmail(String email);

}
