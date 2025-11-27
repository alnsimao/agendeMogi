package com.aln.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aln.project.model.ServiceItem;

public interface ServiceRepository  extends JpaRepository<ServiceItem, Long>{
}
