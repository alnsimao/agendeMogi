package com.aln.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aln.project.exceptions.NotFoundException;
import com.aln.project.model.ServiceItem;
import com.aln.project.repository.ServiceRepository;

@Service
public class ServiceItemService {

    @Autowired
    private ServiceRepository serviceRepository;


    public ServiceItem create(ServiceItem serviceItem) {
        return serviceRepository.save(serviceItem);
    }


    public List<ServiceItem> findAll() {
        return serviceRepository.findAll();
    }


    public ServiceItem findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado"));
    }


    public ServiceItem update(Long id, ServiceItem updated) {
        ServiceItem saved = this.findById(id);

        saved.setName(updated.getName());
        saved.setPrice(updated.getPrice());
        saved.setCategory(updated.getCategory());
        saved.setDurationMinutes(updated.getDurationMinutes());

        return serviceRepository.save(saved);
    }


    public void delete(Long id) {
        ServiceItem serviceItem = this.findById(id);
        serviceRepository.delete(serviceItem);
    }
}
