package com.aln.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aln.project.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
