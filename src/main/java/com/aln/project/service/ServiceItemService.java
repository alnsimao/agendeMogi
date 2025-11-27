package com.aln.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aln.project.model.ServiceItem;
import com.aln.project.repository.ServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceItemService {

    private final ServiceRepository repository;

    public ServiceItem create(ServiceItem item) {
        item.setActive(true);
        return repository.save(item);
    }

    public List<ServiceItem> listAll() {
        return repository.findAll();
    }

    public ServiceItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));
    }

    public ServiceItem update(Long id, ServiceItem updated) {
        ServiceItem item = findById(id);

        item.setName(updated.getName());
        item.setPrice(updated.getPrice());
        item.setCategory(updated.getCategory());
        item.setDurationMinutes(updated.getDurationMinutes());

        return repository.save(item);
    }

   
    public ServiceItem cancel(Long id) {
        ServiceItem item = findById(id);

        item.setActive(false);

        return repository.save(item);
    }
}
