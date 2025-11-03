package com.aln.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aln.project.model.Service;

public interface ServiceRepository  extends JpaRepository<Service, Long>{
}
